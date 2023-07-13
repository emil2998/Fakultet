// Fill out your copyright notice in the Description page of Project Settings.

#pragma once

#include "CoreMinimal.h"
#include "GameFramework/Character.h"
#include "SCharacter.generated.h"

class UCameraComponent;
class USpringArmComponent;
class ASWeapon;
class USHealthComponent;
class USoundCue;
class UPawnNoiseEmitterComponent;

UCLASS()
class COOPGAME_API ASCharacter : public ACharacter
{
	GENERATED_BODY()

public:
	// Sets default values for this character's properties
	ASCharacter();

protected:
	// Called when the game starts or when spawned
	virtual void BeginPlay() override;

	void MoveForward(float Value);

	void MoveRight(float Value);

	void BeginCrouch();

	void EndCrouch();

	void BeginSprint();

	void EndSprint();

	UPROPERTY(VisibleAnywhere, BlueprintReadOnly, Category = "Components")
	UCameraComponent* CameraComp;

	UPROPERTY(VisibleAnywhere, BlueprintReadOnly, Category = "Components")
	USpringArmComponent* SpringArmComp;

	UPROPERTY(VisibleAnywhere, BlueprintReadOnly, Category = "Components")
	USHealthComponent* HealthComp;

	UPROPERTY(BlueprintReadOnly, Category = "Player")
	bool bWantsToSprint;

	UPROPERTY(BlueprintReadOnly, Category = "Player")
	bool bCanSprint;

	UPROPERTY(BlueprintReadOnly, Category = "Player")
	bool bWantsToZoom;

	UPROPERTY(EditDefaultsOnly, Category = "Player")
	float ZoomedFOV;

	UPROPERTY(EditDefaultsOnly, Category = "Player", meta = (ClampMin = 0.1, ClampMax = 100))
	float ZoomInterpSpeed;

	/* Default FOV set during begin play */
	
	float DefaultFOV;

	void BeginZoom();

	void EndZoom();

	UPROPERTY(EditDefaultsOnly, Category = "Player")
	TSubclassOf<ASWeapon> StarterWeaponClass;

	UPROPERTY(VisibleDefaultsOnly, Category = "Player")
	FName WeaponAttachSocketName;

	void OnReload();

	void StartFire();

	void StopFire();

	UFUNCTION()
	void OnHealthChanged(USHealthComponent* InHealthComp, float Health, float HealthDelta, const class UDamageType* DamageType, class AController* InstigatedBy, AActor* DamageCauser);

	/* Pawn died previously */
	UPROPERTY(BlueprintReadOnly, Category = "Player")
	bool bDied;

	/* Deal damage to the Actor that was hit by the punch animation */
	UFUNCTION(BlueprintCallable, Category = "Attacking")
	void PerformMeleeStrike(AActor* HitActor);

	UFUNCTION(BlueprintCallable, Reliable, NetMulticast)
	void SimulateMeleeStrike();

	void SimulateMeleeStrike_Implementation();

	UPROPERTY(EditDefaultsOnly, Category = "Attacking")
	TSubclassOf<UDamageType> PunchDamageType;

	UPROPERTY(EditDefaultsOnly, Category = "Attacking")
	float MeleeDamage;

	UPROPERTY(EditDefaultsOnly, Category = "Attacking")
	UAnimMontage* MeleeAnimMontage;

	float LastMeleeAttackTime;

	UPROPERTY(EditDefaultsOnly, Category = "Player")
	USoundCue* DeathSound;

	UPROPERTY(EditDefaultsOnly, Category = "Weapon")
	USoundCue* ReloadSound;

	FTimerHandle ReloadTimer;

	UFUNCTION()
	void onTimerEnd();

	UPROPERTY(VisibleAnywhere, BlueprintReadOnly, Category = "AI")
	UPawnNoiseEmitterComponent* NoiseEmitterComponent;

public:	
	// Called every frame
	virtual void Tick(float DeltaTime) override;

	ASWeapon* CurrentWeapon;

	UPROPERTY(BlueprintReadOnly, Category = "Player")
	bool isReloading;

	UPROPERTY(BlueprintReadWrite, Category = "Player")
	bool bWantsToReload;

	// Called to bind functionality to input
	virtual void SetupPlayerInputComponent(class UInputComponent* PlayerInputComponent) override;

	virtual FVector GetPawnViewLocation() const override;
};
