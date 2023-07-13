// Fill out your copyright notice in the Description page of Project Settings.


#include "SCharacter.h"
#include "Camera/CameraComponent.h"
#include "GameFramework/SpringArmComponent.h"
#include "GameFramework/PawnMovementComponent.h"
#include "Components/CapsuleComponent.h"
#include "CoopGame/CoopGame.h"
#include "Components/SHealthComponent.h"
#include "Sound/SoundCue.h"
#include "Kismet/GameplayStatics.h"
#include "TimerManager.h"
#include "SWeapon.h"
#include "Components/PawnNoiseEmitterComponent.h"


// Sets default values
ASCharacter::ASCharacter()
{
 	// Set this character to call Tick() every frame.  You can turn this off to improve performance if you don't need it.
	PrimaryActorTick.bCanEverTick = true;

	SpringArmComp = CreateDefaultSubobject<USpringArmComponent>(TEXT("SpringArmComp"));
	SpringArmComp->bUsePawnControlRotation = true;
	SpringArmComp->SetupAttachment(RootComponent);

	GetMovementComponent()->GetNavAgentPropertiesRef().bCanCrouch = true;

	GetCapsuleComponent()->SetCollisionResponseToChannel(COLLISION_WEAPON, ECR_Ignore);

	HealthComp = CreateDefaultSubobject<USHealthComponent>(TEXT("HealthComp"));

	CameraComp = CreateDefaultSubobject<UCameraComponent>(TEXT("CameraComp"));
	CameraComp->SetupAttachment(SpringArmComp);

	ZoomedFOV = 65.0f;
	ZoomInterpSpeed = 20;

	WeaponAttachSocketName = "WeaponSocket";

	bWantsToReload = false;

	NoiseEmitterComponent = CreateDefaultSubobject<UPawnNoiseEmitterComponent>(TEXT("NoiseEmitter"));
}

// Called when the game starts or when spawned
void ASCharacter::BeginPlay()
{
	Super::BeginPlay();

	DefaultFOV = CameraComp->FieldOfView;

	// Spawn a default weapon
	FActorSpawnParameters SpawnParams;
	SpawnParams.SpawnCollisionHandlingOverride = ESpawnActorCollisionHandlingMethod::AlwaysSpawn;

	CurrentWeapon = GetWorld()->SpawnActor<ASWeapon>(StarterWeaponClass, FVector::ZeroVector, FRotator::ZeroRotator, SpawnParams);
	if (CurrentWeapon)
	{   
		CurrentWeapon->SetOwner(this);
		CurrentWeapon->AttachToComponent(GetMesh(), FAttachmentTransformRules::SnapToTargetNotIncludingScale, WeaponAttachSocketName);
	}

	HealthComp->OnHealthChanged.AddDynamic(this, &ASCharacter::OnHealthChanged);
}

void ASCharacter::MoveForward(float Value)
{
	AddMovementInput(GetActorForwardVector() * Value);

	if (GetVelocity().IsZero() || (FVector::DotProduct(GetVelocity().GetSafeNormal2D(), GetActorRotation().Vector()) < 0.8))
	{
		bWantsToSprint = false;
	}
}

void ASCharacter::MoveRight(float Value)
{	
	AddMovementInput(GetActorRightVector() * Value);	

	if (GetVelocity().IsZero() || (FVector::DotProduct(GetVelocity().GetSafeNormal2D(), GetActorRotation().Vector()) < 0.8 ))
	{
		bWantsToSprint = false;
	}
}

void ASCharacter::BeginCrouch()
{
	Crouch();
	bWantsToSprint = false;
}

void ASCharacter::EndCrouch()
{
	UnCrouch();	
}

void ASCharacter::BeginSprint()
{	
	bWantsToReload = false;
	bWantsToSprint = true;
	if (CurrentWeapon)
	{
		CurrentWeapon->StopFire();
	}	
}

void ASCharacter::EndSprint()
{	
	bWantsToSprint = false;
}

void ASCharacter::BeginZoom()
{
	bWantsToZoom = true;
	if (bWantsToSprint) 
	{
		bWantsToSprint = false;
	}
}

void ASCharacter::EndZoom()
{
	bWantsToZoom = false;
}

void ASCharacter::OnReload()
{
	if (CurrentWeapon)
	{	
		CurrentWeapon->StopFire();

		if (isReloading == true)
		{
			return;
		}
		else
		{		
			UGameplayStatics::SpawnSoundAttached(ReloadSound, GetMesh(), WeaponAttachSocketName);
			isReloading = true;
			GetWorld()->GetTimerManager().SetTimer(ReloadTimer, this, &ASCharacter::onTimerEnd, 2.1f, false);						
		}	
	}
}

void ASCharacter::StartFire()
{	
	if (bWantsToSprint == false && isReloading== false)
	{
		if (CurrentWeapon)
		{
			CurrentWeapon->StartFire();
		}
	}
}

void ASCharacter::StopFire()
{
	if (CurrentWeapon)
	{
		CurrentWeapon->StopFire();
	}
}

void ASCharacter::OnHealthChanged(USHealthComponent* InHealthComp, float Health, float HealthDelta, const class UDamageType* DamageType, class AController* InstigatedBy, AActor* DamageCauser)
{	
	if (Health <= 0.0f && !bDied)
	{
		// Die!
		bDied = true;
		if (bDied) 
		{	
			UGameplayStatics::PlaySoundAtLocation(this, DeathSound, GetActorLocation());
		}
	
		GetMovementComponent()->StopMovementImmediately();
		GetCapsuleComponent()->SetCollisionEnabled(ECollisionEnabled::NoCollision);

		DetachFromControllerPendingDestroy();

		SetLifeSpan(1.0f);
		if (CurrentWeapon)
		{
			CurrentWeapon->SetLifeSpan(1.0f);
		}		
	}
}

void ASCharacter::PerformMeleeStrike(AActor* HitActor)
{	
	if (HitActor && HitActor != this && !bDied)
	{
		ACharacter* OtherPawn = Cast<ACharacter>(HitActor);
		if (OtherPawn)
		{			
				FPointDamageEvent DmgEvent;
				DmgEvent.Damage = MeleeDamage;
				HitActor->TakeDamage(DmgEvent.Damage, DmgEvent, GetController(), this);			
		}
	}
}

void ASCharacter::SimulateMeleeStrike_Implementation()
{	
	PlayAnimMontage(MeleeAnimMontage);
}


void ASCharacter::onTimerEnd()
{ 
	CurrentWeapon->OnReload();
	isReloading = false;
}

// Called every frame
void ASCharacter::Tick(float DeltaTime)
{
	Super::Tick(DeltaTime);

	float TargetFOV = bWantsToZoom ? ZoomedFOV : DefaultFOV;

	float NewFOV = FMath::FInterpTo(CameraComp->FieldOfView, TargetFOV, DeltaTime, ZoomInterpSpeed);

	CameraComp->SetFieldOfView(NewFOV);
}


// Called to bind functionality to input
void ASCharacter::SetupPlayerInputComponent(UInputComponent* PlayerInputComponent)
{
	Super::SetupPlayerInputComponent(PlayerInputComponent);

	PlayerInputComponent->BindAxis("MoveForward", this, &ASCharacter::MoveForward);
	PlayerInputComponent->BindAxis("MoveRight", this, &ASCharacter::MoveRight);
	
	PlayerInputComponent->BindAxis("LookUp", this, &ASCharacter::AddControllerPitchInput);
	PlayerInputComponent->BindAxis("Turn", this, &ASCharacter::AddControllerYawInput);

	PlayerInputComponent->BindAction("Crouch", IE_Pressed, this, &ASCharacter::BeginCrouch);
	PlayerInputComponent->BindAction("Crouch", IE_Released, this, &ASCharacter::EndCrouch);

	PlayerInputComponent->BindAction("Jump", IE_Pressed, this, &ACharacter::Jump);

	PlayerInputComponent->BindAction("Sprint", IE_Pressed, this, &ASCharacter::BeginSprint);
	PlayerInputComponent->BindAction("Sprint", IE_Released, this, &ASCharacter::EndSprint);

	PlayerInputComponent->BindAction("Zoom", IE_Pressed, this, &ASCharacter::BeginZoom);
	PlayerInputComponent->BindAction("Zoom", IE_Released, this, &ASCharacter::EndZoom);

	PlayerInputComponent->BindAction("Fire", IE_Pressed, this, &ASCharacter::StartFire);
	PlayerInputComponent->BindAction("Fire", IE_Released, this, &ASCharacter::StopFire);

	

	PlayerInputComponent->BindAction("Reload", IE_Pressed, this, &ASCharacter::OnReload);
}

FVector ASCharacter::GetPawnViewLocation() const
{
	if (CameraComp)
	{
		return CameraComp->GetComponentLocation();

	}
	return Super::GetPawnViewLocation();
}











