package hr.foi.air.air2116.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.*
import com.google.firebase.firestore.DocumentSnapshot

import com.google.firebase.storage.FirebaseStorage
import java.util.*
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.air.air2116.dataClass.*

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import hr.foi.air.core.Korisnik
import hr.foi.air.core.Prijevozi
import hr.foi.air.core.Popravak


fun FirestoreDohvatiKorisnike(myCallBack: FirestoreCallBack) {

    var listaKorisnika = mutableListOf<Korisnik>()
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    firebaseFirestore.collection("Korisnik")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    val korisnickoIme0 = document.data.getValue("korisnickoIme").toString()
                    val lozinka0 = document.data.getValue("lozinka").toString()
                    val ime0 = document.data.getValue("ime").toString()
                    val prezime0 = document.data.getValue("prezime").toString()
                    val uloga0 = document.data.getValue("uloga").toString()
                    val pin0 = document.data.getValue("pin").toString()
                    val id = document.id
                    val slikaURL = document.data.getValue("slikaURL").toString()
                    val mobitel = document.data.getValue("mobitel").toString()
                    val email = document.data.getValue("email").toString()
                    val datumRodenja = document.data.getValue("datumRodenja").toString()

                    var godina: String = datumRodenja.split("/")[0]
                    var mjesec: String = datumRodenja.split("/")[1]
                    var dan: String = datumRodenja.split("/")[2]
                    val datum = Date(godina.toInt(), mjesec.toInt(), dan.toInt())

                    listaKorisnika.add(
                        Korisnik(
                            korisnickoIme0,
                            lozinka0,
                            ime0,
                            prezime0,
                            uloga0,
                            pin0,
                            id,
                            slikaURL,
                            null,
                            mobitel,
                            email,
                            datum
                        )
                    )
                    Log.d("DEBUG_APP", korisnickoIme0)
                }
                myCallBack.onCallback(listaKorisnika)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

fun FirestoreDohvatiVozila(myCallBack: FirestoreCallBackVozilo) {

    var listaVozila = mutableListOf<Vozilo>()
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    val godinaProizvodnje0 = document.data.getValue("godinaProizvodnje").toString()
                    val idVozila0 = document.data.getValue("idVozila").toString()
                    val ispravan0 = document.data.getValue("ispravan").toString()
                    val nazivModela0 = document.data.getValue("nazivModela").toString()
                    val nosivost0 = document.data.getValue("nosivost").toString()
                    val registracija0 = document.data.getValue("registracija").toString()
                    val registriranDo0=document.data.getValue("registriranDo").toString()
                    val slikaURL0 = document.data.getValue("slika").toString()
                    val vrstaVozila0 = document.data.getValue("vrstaVozila").toString()

                    var godina: String = registriranDo0.split("/")[0]
                    var mjesec: String = registriranDo0.split("/")[1]
                    var dan: String = registriranDo0.split("/")[2]
                    val registriranDo1 = Date(godina.toInt(), mjesec.toInt(), dan.toInt())

                    var snaga0:String="-"

                    if(vrstaVozila0=="Kamion"){
                        snaga0 = document.data.getValue("snaga").toString()
                    }

                    listaVozila.add(
                        Vozilo(idVozila0,ispravan0,godinaProizvodnje0,nazivModela0,nosivost0,registracija0,registriranDo1,slikaURL0,null,snaga0,vrstaVozila0)
                    )

                }
                myCallBack.onCallback(listaVozila)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

fun FirestorDohvatiSlikeVozila(listaVozila: List<Vozilo>, myCallBack: FirestoreCallBackVozilo) {
    val listaVozilaSaSlikom = mutableListOf<Vozilo>()
    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    var brojac = 0
    for (vozilo in listaVozila) {
        val firebaseStorageReference =
            firebaseStorage.getReference().child("vozilo").child(vozilo.slikaURL)
        firebaseStorageReference.getBytes(1024 * 1024).addOnCompleteListener {
            if (it.isComplete) {
                val bitmap = BitmapFactory.decodeByteArray(it.result, 0, it.result.size)
                vozilo.slika = bitmap
                listaVozilaSaSlikom.add(vozilo)
                brojac++
                if(brojac==listaVozila.size)
                myCallBack.onCallback(listaVozilaSaSlikom)
            }
        }.addOnFailureListener {
            Log.w("Neuspjeh", "Neuspjšeno dohvaćanje slike")
        }
    }
}

fun FirestoreUnesiVozilo(vozilo: Vozilo) {
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var datumRegistriranoDo:String=""
    if (vozilo.registriranDo!=null){
        datumRegistriranoDo= vozilo.registriranDo!!.year.toString() + "/" + vozilo.registriranDo!!.month.toString() + "/" + vozilo.registriranDo!!.day.toString()
    }

    val vozila = firebaseFirestore.collection("Vozilo")
    val id = vozila.document().id
    vozilo.idVozila = id
    val voziloZapis = hashMapOf(
        "godinaProizvodnje" to vozilo.godinaProizvodnje,
        "idVozila" to vozilo.idVozila,
        "ispravan" to vozilo.ispravan,
        "nazivModela" to vozilo.nazivModela,
        "nosivost" to vozilo.nosivost,
        "registracija" to vozilo.registracija,
        "registriranDo" to datumRegistriranoDo,
        "slika" to vozilo.slikaURL,
        "snaga" to vozilo.snaga,
        "vrstaVozila" to vozilo.vrstaVozila,
    )
    vozila.document(id).set(voziloZapis)
}

interface FirestoreCallBackVozilo {
    fun onCallback(lista: MutableList<Vozilo>)
}

fun FirestorDohvatiSlikeKorisnika(listaKorisnika: List<Korisnik>, myCallBack: FirestoreCallBack) {
    val listaKorisnikaSaSlikom = mutableListOf<Korisnik>()
    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    for (korisnik in listaKorisnika) {
        val firebaseStorageReference =
            firebaseStorage.getReference().child("korisnik").child(korisnik.slikaURL)
        firebaseStorageReference.getBytes(1024 * 1024).addOnCompleteListener {
            if (it.isComplete) {
                val bitmap = BitmapFactory.decodeByteArray(it.result, 0, it.result.size)
                korisnik.slika = bitmap
                listaKorisnikaSaSlikom.add(korisnik)
                myCallBack.onCallback(listaKorisnikaSaSlikom)
            }
        }.addOnFailureListener {
            Log.w("Neuspjeh", "Neuspjšeno dohvaćanje slike")
        }
    }
}

interface FirestoreCallBack {
    fun onCallback(lista: MutableList<Korisnik>)
}

interface FirestoreCallBackDogovoreni {
    fun onCallback(lista: MutableList<DogovoreniPosao>)
}

fun FirestoreUnesiKorisnika(korisnik: Korisnik) {
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val datumRodenja: String =
        korisnik.datumRodenja.year.toString() + "/" + korisnik.datumRodenja.month.toString() + "/" + korisnik.datumRodenja.day.toString()

    val korisnici = firebaseFirestore.collection("Korisnik")
    val id = korisnici.document().id
    korisnik.id = id
    val korisnikZapis = hashMapOf(
        "korisnickoIme" to korisnik.korisnickoIme,
        "lozinka" to korisnik.lozinka,
        "ime" to korisnik.ime,
        "prezime" to korisnik.prezime,
        "uloga" to korisnik.uloga,
        "pin" to korisnik.pin,
        "id" to korisnik.id,
        "slikaURL" to korisnik.slikaURL,
        "mobitel" to korisnik.mobitel,
        "email" to korisnik.email,
        "datumRodenja" to datumRodenja
    )
    korisnici.document(id).set(korisnikZapis)
}

fun FirestoreUpdateKorisnika(korisnik: Korisnik) {
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val korisnici = firebaseFirestore.collection("Korisnik")
    val datumRodenja: String =
        korisnik.datumRodenja.year.toString() + "/" + korisnik.datumRodenja.month.toString() + "/" + korisnik.datumRodenja.day.toString()


    val korisnikZapis = hashMapOf(
        "korisnickoIme" to korisnik.korisnickoIme,
        "lozinka" to korisnik.lozinka,
        "ime" to korisnik.ime,
        "prezime" to korisnik.prezime,
        "uloga" to korisnik.uloga,
        "pin" to korisnik.pin,
        "id" to korisnik.id,
        "slikaURL" to korisnik.slikaURL,
        "mobitel" to korisnik.mobitel,
        "email" to korisnik.email,
        "datumRodenja" to datumRodenja
    )
    korisnici.document(korisnik.id).set(korisnikZapis)
}

fun FirestoreDeleteKorisnika(korisnik: Korisnik) {
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val id = korisnik.id

    firebaseFirestore.collection("Korisnik").document(id).delete().addOnSuccessListener {
        Log.d("TAG", "Dokument je uspješno obrisan!")
    }.addOnFailureListener { e ->
        Log.w("TAG", "Brisanje korisnika nije uspjelo!", e)
    }
}

fun FirestoreDeleteVozilo(vozilo:Vozilo){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val id = vozilo.idVozila
    firebaseFirestore.collection("Vozilo").document(id).delete().addOnSuccessListener {
    }
}

fun FirestorePromjeniSlikuVozila(StaraSlikaNaziv:String,NovaSlikaUri:Uri,vozilo:Vozilo,myCallBack: FirestoreCallBackSlikaVozila){

    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    val firebaseStorageReference = firebaseStorage.getReference().child("vozilo").child(StaraSlikaNaziv)
    if(StaraSlikaNaziv!="kamionDefault.jpg" && StaraSlikaNaziv!="prikolicaDefault.jpg") {
        firebaseStorageReference.delete().addOnCompleteListener {
            val firebaseStorageReferenceNew =
                firebaseStorage.getReference("vozilo/${vozilo.idVozila}")
            firebaseStorageReferenceNew.putFile(NovaSlikaUri).addOnCompleteListener {
                myCallBack.onCallback()
            }
        }
    }
    if(StaraSlikaNaziv=="kamionDefault.jpg" || StaraSlikaNaziv=="prikolicaDefault.jpg"){
        val firebaseStorageReferenceNew =
            firebaseStorage.getReference("vozilo/${vozilo.idVozila}")
        firebaseStorageReferenceNew.putFile(NovaSlikaUri as Uri).addOnCompleteListener {
            myCallBack.onCallback()
        }
    }
}

interface FirestoreCallBackSlikaVozila {
    fun onCallback()
}

fun FirestoreUpdateVozilo(vozilo:Vozilo,myCallBack: FirestoreCallBackUpdateVozila) {
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val vozila = firebaseFirestore.collection("Vozilo")
    val registriranDo: String =
        vozilo.registriranDo?.year.toString() + "/" + vozilo.registriranDo?.month.toString() + "/" + vozilo.registriranDo?.day.toString()

    val voziloZapis = hashMapOf(
        "godinaProizvodnje" to vozilo.godinaProizvodnje,
        "idVozila" to vozilo.idVozila,
        "ispravan" to vozilo.ispravan,
        "nazivModela" to vozilo.nazivModela,
        "nosivost" to vozilo.nosivost,
        "registracija" to vozilo.registracija,
        "slika" to vozilo.slikaURL,
        "snaga" to vozilo.snaga,
        "vrstaVozila" to vozilo.vrstaVozila,
        "registriranDo" to registriranDo
    )
    vozila.document(vozilo.idVozila).set(voziloZapis).addOnCompleteListener {
        myCallBack.onCallBack()
    }
}

interface FirestoreCallBackUpdateVozila{
    fun onCallBack()
}

//primjer pretvaranja dohvaćnog specifičnog dokumenta u objekt
/*fun readData(){
    val docRef=firebaseFirestore.collection("Korisnik").document("korisnik1")
    docRef.get().addOnSuccessListener { documentSnapshot->
        val korisnik = documentSnapshot.toObject<Korisnik>()
    }
}*/

//primjer dohvaćanja više dokumenata odjednom
/*fun readData(){
    firebaseFirestore.collection("Korisnik")
        .whereEqualTo("uloga","admin")
        .get()
        .addOnSuccessListener { documents->
            for(document in documents){
                Log.d("Tag","${document.id}=>${document.data}")
            }
        }
        .addOnFailureListener{exception->
            Log.w("Tag","Error getting documents: ",exception)
        }
}*/

//1. primjer
// Access a Cloud Firestore instance from your Activity
/*fun readData(){
    firebaseFirestore.collection("Korisnik")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}*/

//primjer spremanja novog dokumenta u bazu
/*
fun readData(){
    val korisnici=firebaseFirestore.collection("Korisnik")
    val data1 = hashMapOf(
        "korisnickoIme" to "admin2",
        "lozinka" to "admin2",
        "ime" to "Ana",
        "prezime" to "Anic",
        "uloga" to "admin",
        "pin" to ""
    )
    korisnici.document("noviKorisnik").set(data1)
}
*/

//primjer dohvaćanja specifičnog dokumenta (jednog)
/*fun readData(){
    val docRef=firebaseFirestore.collection("Korisnik").document("noviKorisnik")
    docRef.get().addOnSuccessListener { document ->
        if (document!=null){
            Log.d("tag","DocumentSnapshot Data: ${document.data}")
        } else{
            Log.d("tag","No such document")
        }
    }.addOnFailureListener{exception->
        Log.d("tag","get failed with ",exception)
    }
}*/

/*
firebaseFirestore.collection("Korisnik")
    .get()
    .addOnSuccessListener { documents->
        for(document in documents){
            korisnickoIme0=document.data.getValue("korisnickoIme").toString()
            lozinka0=document.data.getValue("lozinka").toString()
            ime0=document.data.getValue("ime").toString()
            prezime0=document.data.getValue("prezime").toString()
            uloga0=document.data.getValue("uloga").toString()
            pin0=document.data.getValue("pin").toString()
            listaKorisnika.add(Korisnik(korisnickoIme0,lozinka0,ime0,prezime0,uloga0,pin0))
        }
    }
    .addOnFailureListener{exception->
        Log.w("Tag","Error getting documents: ",exception)
    }

 */

//Funkcija koja dohvaća sve dogovorene poslove iz FireStore'a
fun FirestoreDohvatiDogovoreniPosao(myCallBack: FirestoreCallBackPoduzece) {

        var listaPoduzeca = arrayListOf<Poduzeca>()
        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        var Poduzece: String
        var Datum: String
        var Status: String
        var AdrsUtovar: String
        var AdrsIstovar: String
        var Roba:String
        var BrojTura: String
        var Kontakt: String
        var Email: String
        var Id: String

        firebaseFirestore.collection("DogovoreniPosao")
            .get()
            .addOnCompleteListener {
                if (it.isComplete) {
                    for (document in it.result!!) {
                        AdrsIstovar = document.data.getValue("adrsIstovar").toString()
                        AdrsUtovar = document.data.getValue("adrsUtovar").toString()
                        BrojTura = document.data.getValue("brojTura").toString()
                        Datum = document.data.getValue("datum").toString()
                        Email = document.data.getValue("email").toString()
                        Kontakt = document.data.getValue("kontakt").toString()
                        Poduzece = document.data.getValue("poduzece").toString()
                        Roba = document.data.getValue("roba").toString()
                        Status = document.data.getValue("status").toString()
                        Id = document.data.getValue("id").toString()


                        listaPoduzeca.add(
                            Poduzeca(
                                Poduzece,
                                Datum,
                                Status,
                                AdrsUtovar,
                                AdrsIstovar,
                                Roba,
                                BrojTura,
                                Kontakt,
                                Email,
                                Id
                            )
                        )
                    }
                    myCallBack.onCallback(listaPoduzeca)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Tag", "Error getting documents: ", exception)
            }
    }

fun Test(document: QueryDocumentSnapshot, myCallBack: FirestoreCallBackJednoPoduzece){
    var Poduzece: String
    var Datum: String
    var Status: String
    var AdrsUtovar: String
    var AdrsIstovar: String
    var Roba:String
    var BrojTura: String
    var Kontakt: String
    var Email: String
    var Id: String

    document.reference.parent.parent?.get()?.addOnCompleteListener(OnCompleteListener<DocumentSnapshot?> { task ->
        if (task.isSuccessful) {
            AdrsIstovar = task.result.data?.getValue("adrsIstovar").toString()
            AdrsUtovar = task.result.data?.getValue("adrsUtovar").toString()
            BrojTura = task.result.data?.getValue("brojTura").toString()
            Datum = task.result.data?.getValue("datum").toString()
            Email = task.result.data?.getValue("email").toString()
            Kontakt = task.result.data?.getValue("kontakt").toString()
            Poduzece = task.result.data?.getValue("poduzece").toString()
            Roba = task.result.data?.getValue("roba").toString()
            Status = task.result.data?.getValue("status").toString()
            Id = task.result.data?.getValue("id").toString()

            val test = Poduzeca(
                Poduzece,
                Datum,
                Status,
                AdrsUtovar,
                AdrsIstovar,
                Roba,
                BrojTura,
                Kontakt,
                Email,
                Id
            )
            myCallBack.onCallback(test)
        }

    })
}

fun FirestoreDohvatiDogovoreniPosaoKorisnik(myCallBack: FirestoreCallBackPoduzece, idTest: String) {

    var listaPoduzeca = arrayListOf<Poduzeca>()
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    firebaseFirestore.collectionGroup("DodijeljeniVozaci").get()
        .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
            if (task.isSuccessful) {
                var broj=0
                var brojac = 0
                for (document in task.result) {
                    if(document.data.getValue("idVozac").toString() == idTest && document.data.getValue("status").toString() == "Dovršeno"){
                        broj++
                    }
                }
                if(broj == 0){
                    myCallBack.onCallback(listaPoduzeca)
                }
                for (document in task.result) {
                    if(document.data.getValue("idVozac").toString() == idTest && document.data.getValue("status").toString() == "Dovršeno") {
                        Test(document, object : FirestoreCallBackJednoPoduzece{
                            override fun onCallback(poduzece: Poduzeca) {
                                brojac++
                                listaPoduzeca.add(poduzece)
                                if(brojac == broj){
                                    myCallBack.onCallback(listaPoduzeca)
                                }
                            }
                        })
                    }
                }
            }
        })
}

fun FirestoreDohvatiVozace(myCallBack: FirestoreCallBackVozaci) {

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var imePrezime: String
    var idVozaca: String
    var listaVozaca = mutableListOf<Vozaci>()


    firebaseFirestore.collection("Korisnik")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    if (document.data.getValue("uloga").toString() == "Vozač"){
                        imePrezime = document.data.getValue("ime").toString() + " " + document.data.getValue("prezime").toString()
                        idVozaca = document.data.getValue("id").toString()
                        listaVozaca.add(
                            Vozaci(
                                imePrezime,
                                idVozaca
                            )
                        )
                    }
                }
                myCallBack.onCallback(listaVozaca)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

//Funkcija koja dohvaća sve kamione iz FireStore'a
fun FirestoreDohvatiKamione(myCallBack: FirestoreCallBackKamioni) {

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var regKamion: String
    var idKamion: String
    var listaKamiona = mutableListOf<Kamioni>()


    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    if (document.data.getValue("vrstaVozila").toString() == "Kamion" && document.data.getValue("ispravan").toString() == "true"){
                        regKamion = document.data.getValue("registracija").toString()
                        idKamion = document.data.getValue("idVozila").toString()
                        listaKamiona.add(
                            Kamioni(
                                regKamion,
                                idKamion
                            )
                        )
                    }
                }
                myCallBack.onCallback(listaKamiona)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

//Funkcija koja dohvaća sve prikolice iz FireStore'a
fun FirestoreDohvatiPrikolice(myCallBack: FirestoreCallBackPrikolice) {

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var regPrikolica: String
    var idPrikolica: String
    var listaPrikolica = mutableListOf<Prikolice>()


    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    if (document.data.getValue("vrstaVozila").toString() == "Prikolica" && document.data.getValue("ispravan").toString() == "true"){
                        regPrikolica = document.data.getValue("registracija").toString()
                        idPrikolica = document.data.getValue("idVozila").toString()
                        listaPrikolica.add(
                            Prikolice(
                                regPrikolica,
                                idPrikolica
                            )
                        )
                    }
                }
                myCallBack.onCallback(listaPrikolica)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

//Funkcija koja dohvaća sve vozače dodijeljene dogovorenom poslu iz FireStore'a
fun FirestoreDohvatiDodijeljeneVozace(idDogovoreniPosao: String, myCallBack: FirestoreCallBackDodijeljeniVozaci) {

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var idDodijeljenVozac: String
    var idPoduzeca: String
    var idInfo: String
    var idVozac: String
    var regKamion: String
    var idKamion: String
    var regPrikolica: String
    var idPrikolica: String
    var status: String
    var vozac: String

    var listaDodijeljenihVozaca = mutableListOf<DodijeljeniVozaci>()


    firebaseFirestore.collection("DogovoreniPosao").document(idDogovoreniPosao).collection("DodijeljeniVozaci")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    idDodijeljenVozac = document.data.getValue("idDodijeljenVozac").toString()
                    idPoduzeca = document.data.getValue("idPoduzeca").toString()
                    idInfo = document.data.getValue("idInfo").toString()
                    idVozac = document.data.getValue("idVozac").toString()
                    regKamion = document.data.getValue("regKamion").toString()
                    idKamion = document.data.getValue("idKamion").toString()
                    regPrikolica = document.data.getValue("regPrikolica").toString()
                    idPrikolica = document.data.getValue("idPrikolica").toString()
                    status = document.data.getValue("status").toString()
                    vozac = document.data.getValue("vozac").toString()

                    listaDodijeljenihVozaca.add(
                        DodijeljeniVozaci(
                            vozac,
                            regKamion,
                            idKamion,
                            regPrikolica,
                            idPrikolica,
                            status,
                            idPoduzeca,
                            idDodijeljenVozac
                        )
                    )
                }
                myCallBack.onCallback(listaDodijeljenihVozaca)
            }
            }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

fun FirestoreDohvatiRedniBroj(idVozaca:String,idInfo:String, myCallBack: FirestoreCallBackRedniBroj ) {
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    firebaseFirestore.collection("Korisnik").document(idVozaca).collection("InfoVozaca")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    var id = document.data.getValue("idInfo").toString()
                    var redniBroj = document.data.getValue("redniBroj").toString()
                    if(id.equals(idInfo )) {
                        myCallBack.onCallback(redniBroj.toInt())
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

fun FirestoreUsporediBrojTura(prijevoz:Prijevozi,brojTura:String){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()
    var status:String
    var Poduzece: String
    var Datum: String
    var AdrsUtovar: String
    var AdrsIstovar: String
    var Roba:String
    var BrojTura: String
    var Kontakt: String
    var Email: String
    var id: String
    firebaseFirestore.collection("DogovoreniPosao").document(prijevoz.idDogovoreniPosao).collection("DodijeljeniVozaci")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                var brojac=0;
                for(document in it.result!!){
                    status = document.data.getValue("status").toString()
                    if(status.equals("Dovršeno"))
                        brojac++
                }
                if(brojac.toString().equals(brojTura)){
                    firebaseFirestore.collection("DogovoreniPosao")
                        .get()
                        .addOnCompleteListener{
                            if(it.isComplete){
                                for(document in it.result!!){
                                    id = document.data.getValue("id").toString()
                                    if(id.equals(prijevoz.idDogovoreniPosao)){
                                        AdrsIstovar = document.data.getValue("adrsIstovar").toString()
                                        AdrsUtovar = document.data.getValue("adrsUtovar").toString()
                                        BrojTura = document.data.getValue("brojTura").toString()
                                        Datum = document.data.getValue("datum").toString()
                                        Email = document.data.getValue("email").toString()
                                        id = document.data.getValue("id").toString()
                                        Kontakt = document.data.getValue("kontakt").toString()
                                        Poduzece = document.data.getValue("poduzece").toString()
                                        Roba = document.data.getValue("roba").toString()
                                        var zapis = firebaseFirestore.collection("DogovoreniPosao")
                                        var noviZapis = hashMapOf(
                                            "adrsIstovar" to AdrsIstovar,
                                            "adrsUtovar" to AdrsUtovar,
                                            "brojTura"  to BrojTura,
                                            "datum" to Datum,
                                            "email" to Email,
                                            "id" to id,
                                            "kontakt" to Kontakt,
                                            "poduzece" to Poduzece,
                                            "roba" to Roba,
                                            "status" to "Dovršeno"
                                        )
                                        zapis.document(id).set(noviZapis)

                                    }
                                }

                            }
                        }
                }

            }
        }
}

fun FirestorePogledajDogPoslove(prijevoz:Prijevozi){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()
    var brojTura:String
    var id:String

    firebaseFirestore.collection("DogovoreniPosao")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    id = document.data.getValue("id").toString()
                    brojTura = document.data.getValue("brojTura").toString()
                    if(id.equals(prijevoz.idDogovoreniPosao)){
                        FirestoreUsporediBrojTura(prijevoz,brojTura)
                    }
                }

            }
        }



}


fun FirestoreUrediRedneBrojeve(prijevoz:Prijevozi){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()

    firebaseFirestore.collection("Korisnik").document(prijevoz.idVozac).collection("InfoVozaca").document(prijevoz.idInfo).delete().addOnSuccessListener {
        Log.d("TAG", "Dokument je uspješno obrisan!")
    }.addOnFailureListener { e ->
        Log.w("TAG", "Brisanje korisnika nije uspjelo!", e)
    }
    var idDodijeljenVozac:String
    var idInfo:String
    var idKamion:String
    var idPoduzeca:String
    var idPrikolica:String
    var idVozac:String
    var redniBroj:String
    var regKamion:String
    var regPrikolica:String
    firebaseFirestore.collection("Korisnik").document(prijevoz.idVozac).collection("InfoVozaca")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    idDodijeljenVozac = document.data.getValue("idDodijeljenVozac").toString()
                    idInfo = document.data.getValue("idInfo").toString()
                    idKamion = document.data.getValue("idKamion").toString()
                    idPoduzeca = document.data.getValue("idPoduzeca").toString()
                    idPrikolica = document.data.getValue("idPrikolica").toString()
                    idVozac = document.data.getValue("idVozac").toString()
                    redniBroj = document.data.getValue("redniBroj").toString()
                    regKamion = document.data.getValue("regKamion").toString()
                    regPrikolica = document.data.getValue("regPrikolica").toString()
                    var infoVozaca = firebaseFirestore.collection("Korisnik").document(prijevoz.idVozac).collection("InfoVozaca")
                    var noviInfo = hashMapOf(
                        "idDodijeljenVozac" to idDodijeljenVozac,
                        "idInfo" to idInfo,
                        "idKamion" to idKamion,
                        "idPoduzeca" to idPoduzeca,
                        "idPrikolica" to idPrikolica,
                        "idVozac" to idVozac,
                        "redniBroj" to (redniBroj.toInt()-1),
                        "regKamion" to regKamion,
                        "regPrikolica" to regPrikolica
                    )
                    infoVozaca.document(idInfo).set(noviInfo)
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

fun FirestoreIstovariPrijevoz(prijevoz: Prijevozi){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val prijevozBaza = firebaseFirestore.collection("DogovoreniPosao").document(prijevoz.idDogovoreniPosao).collection("DodijeljeniVozaci")
    val noviPrijevoz = hashMapOf(
        "idDodijeljenVozac" to prijevoz.idDodijeljenVozac,
        "idInfo" to prijevoz.idInfo,
        "idKamion" to prijevoz.idKamion,
        "idPoduzeca" to prijevoz.idPoduzeca,
        "idPrikolica" to prijevoz.idPrikolica,
        "idVozac" to prijevoz.idVozac,
        "regKamion" to prijevoz.regKamion,
        "regPrikolica" to prijevoz.regPrikolica,
        "status" to "Dovršeno",
        "utovaren" to prijevoz.utovaren,
        "vozac" to prijevoz.vozac
    )
    prijevozBaza.document(prijevoz.idDodijeljenVozac).set(noviPrijevoz)
}

fun FirestoreUtovariPrijevoz(prijevoz: Prijevozi){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val prijevozBaza = firebaseFirestore.collection("DogovoreniPosao").document(prijevoz.idDogovoreniPosao).collection("DodijeljeniVozaci")



    val noviPrijevoz = hashMapOf(
        "idDodijeljenVozac" to prijevoz.idDodijeljenVozac,
        "idInfo" to prijevoz.idInfo,
        "idKamion" to prijevoz.idKamion,
        "idPoduzeca" to prijevoz.idPoduzeca,
        "idPrikolica" to prijevoz.idPrikolica,
        "idVozac" to prijevoz.idVozac,
        "regKamion" to prijevoz.regKamion,
        "regPrikolica" to prijevoz.regPrikolica,
        "status" to prijevoz.status,
        "utovaren" to "Da",
        "vozac" to prijevoz.vozac
    )
    prijevozBaza.document(prijevoz.idDodijeljenVozac).set(noviPrijevoz)

}
fun FirestoreDohvatiDodVozace(idDogovoreniPosao: String, idUlogiranogVozaca:String, myCallBack: FirestoreCallBackDodVozace ){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var idDodijeljeniVozac: String
    var idInfo: String
    var idVozac:String
    var idKamion:String
    var idPrikolica:String
    var regKamion:String
    var regPrikolica:String
    var status:String
    var utovaren:String
    var idPoduzeca:String
    var vozac:String
    var listaDodVozaca = mutableListOf<DodVozaci>()
    firebaseFirestore.collection("DogovoreniPosao").document(idDogovoreniPosao).collection("DodijeljeniVozaci")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    idDodijeljeniVozac = document.data.getValue("idDodijeljenVozac").toString()
                    idInfo = document.data.getValue("idInfo").toString()
                    idKamion = document.data.getValue("idKamion").toString()
                    idPoduzeca = document.data.getValue("idPoduzeca").toString()
                    idPrikolica = document.data.getValue("idPrikolica").toString()
                    idVozac = document.data.getValue("idVozac").toString()
                    regKamion = document.data.getValue("regKamion").toString()
                    regPrikolica = document.data.getValue("regPrikolica").toString()
                    status = document.data.getValue("status").toString()
                    utovaren = document.data.getValue("utovaren").toString()
                    vozac = document.data.getValue("vozac").toString()
                    if(idUlogiranogVozaca.equals(idVozac)&& status.equals("Nedovršeno")){
                        listaDodVozaca.add(
                            DodVozaci(
                                idDodijeljeniVozac,
                                idInfo,
                                idKamion,
                                idPoduzeca,
                                idPrikolica,

                                idVozac,
                                regKamion,
                                regPrikolica,
                                status,
                                utovaren,
                                vozac
                            )
                        )
                    }
                }
                myCallBack.onCallback(listaDodVozaca)
            }
            }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

fun FirestoreDohvatiAdreseIstovarUtovar(myCallBack: FirestoreCallBackAdreseIstovarUtovar){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var idDogovoreniPosao:String
    var adresaUtovar:String
    var adresaIstovara:String
    var listaAdresa = mutableListOf<AdreseIstovarUtovar>()
    firebaseFirestore.collection("DogovoreniPosao")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    idDogovoreniPosao = document.data.getValue("id").toString()
                    adresaUtovar = document.data.getValue("adrsUtovar").toString()
                    adresaIstovara = document.data.getValue("adrsIstovar").toString()
                    listaAdresa.add(
                        AdreseIstovarUtovar(
                            idDogovoreniPosao,
                            adresaUtovar,
                            adresaIstovara
                        )
                    )
                }
                myCallBack.onCallback(listaAdresa)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}
fun FirestoreDohvatiIDVozila(myCallBack: FirestoreCallBackIDVozila){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var idVozila:String
    var ispravan:String
    var listaIDVozila = mutableListOf<String>()
    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    idVozila = document.data.getValue("idVozila").toString()
                    ispravan = document.data.getValue("ispravan").toString()
                    if(ispravan.equals("false")){
                        listaIDVozila.add(idVozila)
                    }
                }
                myCallBack.onCallback(listaIDVozila)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}
fun FirestorePopraviKvar(popravak: Popravak){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var datumKvara:String
    var datumPopravka:String
    var hitnost:String
    var idKvar:String
    var idVozilo:String
    var opisKvara:String
    var opisPopravka:String
    var popravljeno:String
    var godinaProizvodnje:String
    var idVozila:String
    var nazivModela:String
    var nosivost:String
    var registracija:String
    var registriranDo:String
    var slika:String
    var snaga:String
    var vrstaVozila:String
    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    godinaProizvodnje = document.data.getValue("godinaProizvodnje").toString()
                    idVozila = document.data.getValue("idVozila").toString()
                    nazivModela = document.data.getValue("nazivModela").toString()
                    nosivost = document.data.getValue("nosivost").toString()
                    registracija = document.data.getValue("registracija").toString()
                    registriranDo = document.data.getValue("registriranDo").toString()
                    slika = document.data.getValue("slika").toString()
                    snaga = document.data.getValue("snaga").toString()
                    vrstaVozila = document.data.getValue("vrstaVozila").toString()
                    if(idVozila.equals(popravak.idVozilo)){
                        var ispravakVozila = firebaseFirestore.collection("Vozilo")
                        var voziloZapis = hashMapOf(
                            "godinaProizvodnje" to godinaProizvodnje,
                            "idVozila" to idVozila,
                            "ispravan" to "true",
                            "nazivModela" to nazivModela,
                            "nosivost" to nosivost,
                            "registracija" to registracija,
                            "registriranDo" to registriranDo,
                            "slika" to slika,
                            "snaga" to snaga,
                            "vrstaVozila" to vrstaVozila
                        )
                        ispravakVozila.document(idVozila).set(voziloZapis)
                  }
            }
        }
    }
    firebaseFirestore.collection("Vozilo").document(popravak.idVozilo).collection("Kvar")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document2 in it.result!!){
                    datumKvara = document2.data.getValue("datumKvara").toString()
                    datumPopravka = popravak.datumPopravka
                    hitnost = document2.data.getValue("hitnost").toString()
                    idKvar = document2.data.getValue("idKvar").toString()
                    idVozilo = document2.data.getValue("idVozilo").toString()
                    opisKvara = document2.data.getValue("opisKvara").toString()
                    opisPopravka = popravak.opisPopravka
                    popravljeno = "Da"
                    var zapis = firebaseFirestore.collection("Vozilo").document(popravak.idVozilo).collection("Kvar")
                    var noviZapis = hashMapOf(
                        "datumKvara" to datumKvara,
                        "datumPopravka" to datumPopravka,
                        "hitnost" to hitnost,
                        "idKvar" to idKvar,
                        "idVozilo" to idVozilo,
                        "opisKvara" to opisKvara,
                        "opisPopravka" to opisPopravka,
                        "popravljeno" to popravljeno
                    )
                    zapis.document(idKvar).set(noviZapis)

                }
            }
        }

}

fun FirestoreNadiRegistraciju(idVozila:String, myCallBack: FirestoreNadiRegistraciju){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var registracija:String
    var idVozilo:String
    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    idVozilo = document.data.getValue("idVozila").toString()
                    registracija = document.data.getValue("registracija").toString()
                    if(idVozila.equals(idVozilo)){
                        myCallBack.onCallback(registracija)
                    }
                }
            }
        }


}


fun FirestoreNadiKvarove(myCallBack: FirestoreNadiKvarove){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var idVozila:String
    var ispravan:String
    var datumKvara:String
    var datumPopravka:String
    var hitnost:String
    var idKvar:String
    var idVozilo:String
    var opisKvara:String
    var opisPopravka:String
    var popravljeno:String
    var listaKvarova = mutableListOf<Popravak>()

    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    idVozila = document.data.getValue("idVozila").toString()
                    ispravan = document.data.getValue("ispravan").toString()
                    if(ispravan.equals("false")){
                        println("idVozila: "+idVozila)
                        firebaseFirestore.collection("Vozilo").document(idVozila).collection("Kvar")
                            .get()
                            .addOnCompleteListener{
                                if(it.isComplete){
                                    for(document2 in it.result!!){
                                        datumKvara = document2.data.getValue("datumKvara").toString()
                                        datumPopravka = document2.data.getValue("datumPopravka").toString()
                                        hitnost = document2.data.getValue("hitnost").toString()
                                        idKvar = document2.data.getValue("idKvar").toString()
                                        idVozilo = document2.data.getValue("idVozilo").toString()
                                        opisKvara = document2.data.getValue("opisKvara").toString()
                                        opisPopravka = document2.data.getValue("opisPopravka").toString()
                                        popravljeno = document2.data.getValue("popravljeno").toString()
                                        listaKvarova.add(
                                            Popravak(
                                                datumKvara,
                                                datumPopravka,
                                                hitnost,
                                                idKvar,
                                                idVozilo,
                                                opisKvara,
                                                opisPopravka,
                                                popravljeno
                                            )
                                        )

                                    }
                                    myCallBack.onCallback(listaKvarova)
                                }
                            }
                    }
                }
            }
        }
}

fun FirestorePosloziRedneBrojeve(idKorisnika:String,idVoziloBrisanje:String){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var idDodijeljenVozac:String
    var idInfo:String
    var idKamion:String
    var idPoduzeca:String
    var idPrikolica:String
    var idVozac:String
    var redniBroj:String
    var regKamion:String
    var regPrikolica:String
    var listaInfa = mutableListOf<Info>()

    firebaseFirestore.collection("Korisnik").document(idKorisnika).collection("InfoVozaca")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    idDodijeljenVozac = document.data.getValue("idDodijeljenVozac").toString()
                    idInfo = document.data.getValue("idInfo").toString()
                    idKamion = document.data.getValue("idKamion").toString()
                    idPoduzeca = document.data.getValue("idPoduzeca").toString()
                    idPrikolica = document.data.getValue("idPrikolica").toString()
                    idVozac = document.data.getValue("idVozac").toString()
                    redniBroj = document.data.getValue("redniBroj").toString()
                    regKamion = document.data.getValue("regKamion").toString()
                    regPrikolica = document.data.getValue("regPrikolica").toString()
                    if(!(idKamion.equals(idVoziloBrisanje) || idPrikolica.equals((idVoziloBrisanje)))){
                        println("dodajem u listu info: "+idInfo)
                        listaInfa.add(Info(
                            idDodijeljenVozac,
                            idInfo,
                            idKamion,
                            idPoduzeca,
                            idPrikolica,
                            idVozac,
                            redniBroj,
                            regKamion,
                            regPrikolica
                        ))
                    }
                }
                listaInfa.sortBy { it.redniBroj }
                var brojac=1
                for(item in listaInfa){
                    println("printam ovo")
                    var info = firebaseFirestore.collection("Korisnik").document(item.idVozac).collection("InfoVozaca")
                    var noviZapis = hashMapOf(
                        "idDodijeljenVozac" to item.idDodijeljenVozac,
                        "idInfo" to item.idInfo,
                        "idKamion" to item.idKamion,
                        "idPoduzeca" to item.idPoduzeca,
                        "idPrikolica" to item.idPrikolica,
                        "idVozac" to item.idVozac,
                        "redniBroj" to brojac,
                        "regKamion" to item.regKamion,
                        "regPrikolica" to item.regPrikolica
                    )
                    info.document(item.idInfo).set(noviZapis)
                    brojac++
                }
            }
        }

}

fun FirestoreNadiInfoVozaca(idVoziloBrisanje:String){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var id:String
    var uloga:String
    firebaseFirestore.collection("Korisnik")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    id = document.data.getValue("id").toString()
                    uloga = document.data.getValue("uloga").toString()
                    if(uloga.equals("Vozač")){
                        FirestorePosloziRedneBrojeve(id,idVoziloBrisanje)
                    }
                }
            }
        }

}


fun FirestoreObrisiInfo(idVoziloBrisanje:String){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()

    var idDodijeljenVozac:String
    var idInfo:String
    var idKamion:String
    var idPoduzeca:String
    var idPrikolica:String
    var idVozac:String
    var redniBroj:String
    var regKamion:String
    var regPrikolica:String

    var idVozaca:String
    firebaseFirestore.collection("Korisnik")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document2 in it.result!!){
                    idVozaca = document2.data.getValue("id").toString()

                    firebaseFirestore.collection("Korisnik").document(idVozaca).collection("InfoVozaca")
                        .get()
                        .addOnCompleteListener {
                            if (it.isComplete) {
                                for (document in it.result!!) {
                                    idDodijeljenVozac = document.data.getValue("idDodijeljenVozac").toString()
                                    idInfo = document.data.getValue("idInfo").toString()
                                    idKamion = document.data.getValue("idKamion").toString()
                                    idPoduzeca = document.data.getValue("idPoduzeca").toString()
                                    idPrikolica = document.data.getValue("idPrikolica").toString()
                                    idVozac = document.data.getValue("idVozac").toString()
                                    redniBroj = document.data.getValue("redniBroj").toString()
                                    regKamion = document.data.getValue("regKamion").toString()
                                    regPrikolica = document.data.getValue("regPrikolica").toString()
                                    if(idVoziloBrisanje.equals(idPrikolica) || idVoziloBrisanje.equals(idKamion)){
                                        println("brišem info s id: "+idInfo)
                                        firebaseFirestore.collection("Korisnik").document(idVozac).collection("InfoVozaca").document(idInfo).delete().addOnSuccessListener {
                                            Log.d("TAG", "Dokument je uspješno obrisan!")
                                        }.addOnFailureListener{
                                                e-> Log.w("TAG", "Brisanje dokumenta nije uspjelo!", e)
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

fun FirestoreUrediDodVozace(idDogovoreniPosao: String,idVozilo:String){
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var idDodijeljeniVozac: String
    var idInfo: String
    var idVozac:String
    var idKamion:String
    var idPrikolica:String
    var regKamion:String
    var regPrikolica:String
    var status:String
    var utovaren:String
    var idPoduzeca:String
    var vozac:String
    var listaDodVozaca = mutableListOf<DodVozaci>()
    firebaseFirestore.collection("DogovoreniPosao").document(idDogovoreniPosao).collection("DodijeljeniVozaci")
        .get()
        .addOnCompleteListener{
            if(it.isComplete){
                for(document in it.result!!){
                    idDodijeljeniVozac = document.data.getValue("idDodijeljenVozac").toString()
                    idInfo = document.data.getValue("idInfo").toString()
                    idKamion = document.data.getValue("idKamion").toString()
                    idPoduzeca = document.data.getValue("idPoduzeca").toString()
                    idPrikolica = document.data.getValue("idPrikolica").toString()
                    idVozac = document.data.getValue("idVozac").toString()
                    regKamion = document.data.getValue("regKamion").toString()
                    regPrikolica = document.data.getValue("regPrikolica").toString()
                    status = document.data.getValue("status").toString()
                    utovaren = document.data.getValue("utovaren").toString()
                    vozac = document.data.getValue("vozac").toString()
                    if((idKamion.equals(idVozilo) || idPrikolica.equals(idVozilo)) && status.equals("Nedovršeno")){
                        val noviDodVozac = firebaseFirestore.collection("DogovoreniPosao").document(idDogovoreniPosao).collection("DodijeljeniVozaci")
                        val noviZapis = hashMapOf(
                            "idDodijeljenVozac" to idDodijeljeniVozac,
                            "idInfo" to idInfo,
                            "idKamion" to idKamion,
                            "idPoduzeca" to idPoduzeca,
                            "idPrikolica" to idPrikolica,
                            "idVozac" to idVozac,
                            "regKamion" to regKamion,
                            "regPrikolica" to regPrikolica,
                            "status" to "Kvar",
                            "utovaren" to "Da",
                            "vozac" to vozac
                        )
                        noviDodVozac.document(idDodijeljeniVozac).set(noviZapis)
                    }
                }
            }
        }

}

fun FirestoreUnesiKvar(kvar:Kvar) {
    //dodavanje novog Kvara
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val noviKvar = firebaseFirestore.collection("Vozilo").document(kvar.idVozilo).collection("Kvar")
    val idKvar = noviKvar.document().id
    val noviKvarZapis = hashMapOf(
        "idKvar" to idKvar,
        "idVozilo" to kvar.idVozilo,
        "opisKvara" to kvar.opisKvara,
        "datumKvara" to kvar.datumKvara,
        "hitnost" to kvar.hitnost,
        "popravljeno" to "Ne",
        "opisPopravka" to "",
        "datumPopravka" to ""
    )
    noviKvar.document(idKvar).set(noviKvarZapis)

    var godinaProizvodnje:String
    var idVozila:String
    var nazivModela:String
    var nosivost:String
    var registracija:String
    var registriranDo:String
    var slika:String
    var snaga:String
    var vrstaVozila:String

    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener{
            if(it.isComplete) {
                for (document in it.result!!) {
                    godinaProizvodnje = document.data.getValue("godinaProizvodnje").toString()
                    idVozila = document.data.getValue("idVozila").toString()
                    nazivModela = document.data.getValue("nazivModela").toString()
                    nosivost = document.data.getValue("nosivost").toString()
                    registracija = document.data.getValue("registracija").toString()
                    registriranDo = document.data.getValue("registriranDo").toString()
                    slika = document.data.getValue("slika").toString()
                    snaga = document.data.getValue("snaga").toString()
                    vrstaVozila = document.data.getValue("vrstaVozila").toString()
                    if(idVozila.equals(kvar.idVozilo)){
                        var ispravakVozila = firebaseFirestore.collection("Vozilo")
                        var voziloZapis = hashMapOf(
                            "godinaProizvodnje" to godinaProizvodnje,
                            "idVozila" to idVozila,
                            "ispravan" to "false",
                            "nazivModela" to nazivModela,
                            "nosivost" to nosivost,
                            "registracija" to registracija,
                            "registriranDo" to registriranDo,
                            "slika" to slika,
                            "snaga" to snaga,
                            "vrstaVozila" to vrstaVozila
                        )
                        ispravakVozila.document(idVozila).set(voziloZapis)
                    }

                }
            }
        }


}


//Funkcija koja dohvaća  info vozača od odabranog vozača iz FireStore'a
fun FirestoreDohvatiInfoVozaca(idVozac: String, myCallBack: FirestoreCallBackInfoVozaca) {

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var idDodijeljenVozac: String
    var idPoduzeca: String
    var idInfo: String
    var idVozaca: String = idVozac
    var idKamion: String
    var idPrikolica: String
    var regKamion : String
    var regPrikolica: String
    var redoslijed: Int

    var poduzece: String
    var adresaUtovara: String
    var adresaIsotvara: String

    var listaInfoVozaca = mutableListOf<InfoVozaca>()


    firebaseFirestore.collection("Korisnik").document(idVozac).collection("InfoVozaca")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    idDodijeljenVozac = document.data.getValue("idDodijeljenVozac").toString()
                    idPoduzeca = document.data.getValue("idPoduzeca").toString()
                    idInfo = document.data.getValue("idInfo").toString()
                    idVozaca = document.data.getValue("idVozac").toString()
                    idKamion = document.data.getValue("idKamion").toString()
                    idPrikolica = document.data.getValue("idPrikolica").toString()
                    regKamion = document.data.getValue("regKamion").toString()
                    regPrikolica = document.data.getValue("regPrikolica").toString()
                    redoslijed = document.data.getValue("redniBroj").toString().toInt()

                    listaInfoVozaca.add(
                        InfoVozaca(
                            "poduzece",
                            "adresaUtovara",
                            "adresaIsotvara",
                            redoslijed,
                            idPoduzeca,
                            idVozac,
                            idKamion,
                            idPrikolica,
                            idInfo,
                            regKamion,
                            regPrikolica,
                            idDodijeljenVozac
                        )
                    )

                    firebaseFirestore.collection("DogovoreniPosao").document(idPoduzeca).get().addOnSuccessListener {
                            document -> if (document!=null){

                                poduzece = document.data?.getValue("poduzece").toString()
                                adresaUtovara = document.data?.getValue("adrsUtovar").toString()
                                adresaIsotvara = document.data?.getValue("adrsIstovar").toString()

                            }
                    }
                }
                myCallBack.onCallback(listaInfoVozaca)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}


interface FirestoreCallBackJednoPoduzece {
    fun onCallback(poduzece: Poduzeca)
}

interface FirestoreCallBackPoduzece {
    fun onCallback(lista: ArrayList<Poduzeca>)
}

interface FirestoreNadiKvarove{
    fun onCallback(lista: MutableList<Popravak>)
}
interface FirestoreCallBackVozilaPrikolice {
    fun onCallback(lista: ArrayList<VoziloPrikolica>)
}

interface FirestoreNadiRegistraciju{
    fun onCallback(registracija: String)
}


interface FirestoreCallBackRedniBroj {
    fun onCallback(redniBroj: Number)
}
interface FirestoreCallBackVozaci {
    fun onCallback(lista: MutableList<Vozaci>)
}
interface FirestoreCallBackDodVozace {
    fun onCallback(lista: MutableList<DodVozaci>)
}
interface FirestoreCallBackAdreseIstovarUtovar {
    fun onCallback(lista: MutableList<AdreseIstovarUtovar>)
}
interface FirestoreCallBackIDVozila{
    fun onCallback(lista: MutableList<String>)
}

interface FirestoreCallBackKamioni {
    fun onCallback(lista: MutableList<Kamioni>)
}

interface FirestoreCallBackPrikolice {
    fun onCallback(lista: MutableList<Prikolice>)
}

interface FirestoreCallBackDodijeljeniVozaci {
    fun onCallback(lista: MutableList<DodijeljeniVozaci>)
}

interface FirestoreCallBackInfoVozaca {
    fun onCallback(lista: MutableList<InfoVozaca>)
}

//Funkcija koja unosi novi dogovoreni posao u FireStore
fun FirestoreUnesiDogovoreniPosao(poduzece:Poduzeca) {

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val dogovoreniPosao=firebaseFirestore.collection("DogovoreniPosao")
    val idDogovoreniPosao = dogovoreniPosao.document().id
    poduzece.id = idDogovoreniPosao

    val dodijeljenVozac = firebaseFirestore.collection("DogovoreniPosao").document(idDogovoreniPosao).collection("DodijeljeniVozaci")
    val idDodijeljenVozac = dodijeljenVozac.document().id

    val poduzeceZapis = hashMapOf(
        "poduzece" to poduzece.Poduzece,
        "datum" to poduzece.Datum,
        "status" to poduzece.Status,
        "adrsUtovar" to poduzece.AdrsUtovar,
        "adrsIstovar" to poduzece.AdrsIstovar,
        "roba" to poduzece.Roba,
        "brojTura" to poduzece.BrojTura,
        "kontakt" to poduzece.Kontakt,
        "email" to poduzece.Email,
        "id" to poduzece.id
    )
    dogovoreniPosao.document(idDogovoreniPosao).set(poduzeceZapis)

}

//Funkcija koja dodjeljuje novog vozača dogovorenom poslu
fun FirestoreDodijeliVozaca(vozac: DodijeljeniVozaci, idDogovoreniPosao: String, idVozaca: String) {

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val dodijeljenVozac = firebaseFirestore.collection("DogovoreniPosao").document(idDogovoreniPosao).collection("DodijeljeniVozaci")
    val idDodijeljenVozac = dodijeljenVozac.document().id

    val infoVozaca = firebaseFirestore.collection("Korisnik").document(idVozaca).collection("InfoVozaca")
    val idInfoVozaca = infoVozaca.document().id

    var broj: Int = 0

    firebaseFirestore.collection("Korisnik").document(idVozaca).collection("InfoVozaca")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    broj++
                    }
                val infoVozacaZapis = hashMapOf(
                    "idPoduzeca" to vozac.idPoduzeca,
                    "idVozac" to vozac.idDodijeljenVozac,
                    "idInfo" to idInfoVozaca,
                    "idDodijeljenVozac" to idDodijeljenVozac,
                    "regKamion" to vozac.regKamion,
                    "idKamion" to vozac.idKamion,
                    "regPrikolica" to vozac.regPrikolica,
                    "idPrikolica" to vozac.idPrikolica,
                    "redniBroj" to broj + 1
                )
                infoVozaca.document(idInfoVozaca).set(infoVozacaZapis)

            }
            }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }


    val dodijeljenVozacZapis = hashMapOf(
        "vozac" to vozac.vozac,
        "regKamion" to vozac.regKamion,
        "idKamion" to vozac.idKamion,
        "regPrikolica" to vozac.regPrikolica,
        "idPrikolica" to vozac.idPrikolica,
        "status" to vozac.status,
        "idPoduzeca" to vozac.idPoduzeca,
        "idVozac" to vozac.idDodijeljenVozac,
        "idDodijeljenVozac" to idDodijeljenVozac,
        "idInfo" to idInfoVozaca,
        "utovaren" to "Ne"
        )


    dodijeljenVozac.document(idDodijeljenVozac).set(dodijeljenVozacZapis)

}

//Funkcija koja pomiće redni broj iz info vozača gore
fun FirestoreUpdateInfoGore(idVozac: String, redoslijed: Int){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()

    var redniBrojGore: Int
    var redniBrojDolje: Int

    firebaseFirestore.collection("Korisnik").document(idVozac).collection("InfoVozaca")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    Log.d("TAG", document.data?.getValue("redniBroj").toString())
                    var redniBroj: Int = document.data?.getValue("redniBroj").toString().toInt()

                    if (redniBroj == redoslijed-1) {
                        redniBrojGore = redoslijed
                        val korisnikInfo =
                            firebaseFirestore.collection("Korisnik")
                                .document(idVozac)
                                .collection("InfoVozaca")


                        val infoZapis = hashMapOf(
                            "idDodijeljenVozac" to document.data?.getValue(
                                "idDodijeljenVozac"
                            ).toString(),
                            "idInfo" to document.data?.getValue("idInfo")
                                .toString(),
                            "idPoduzeca" to document.data?.getValue(
                                "idPoduzeca"
                            ).toString(),
                            "idVozac" to document.data?.getValue("idVozac")
                                .toString(),
                            "regKamion" to document.data?.getValue("regKamion")
                                .toString(),
                            "idKamion" to document.data?.getValue("idKamion")
                                .toString(),
                            "regPrikolica" to document.data?.getValue("regPrikolica")
                                .toString(),
                            "idPrikolica" to document.data?.getValue("idPrikolica")
                                .toString(),
                            "redniBroj" to redniBrojGore
                        )
                        korisnikInfo.document(document.data.getValue("idInfo").toString()).set(infoZapis)
                    }

                    if (redniBroj == redoslijed && redoslijed > 1){
                        redniBrojGore = redoslijed - 1
                        val korisnikInfo =
                            firebaseFirestore.collection("Korisnik")
                                .document(idVozac)
                                .collection("InfoVozaca")


                        val infoZapis = hashMapOf(
                            "idDodijeljenVozac" to document.data?.getValue(
                                "idDodijeljenVozac"
                            ).toString(),
                            "idInfo" to document.data?.getValue("idInfo")
                                .toString(),
                            "idPoduzeca" to document.data?.getValue(
                                "idPoduzeca"
                            ).toString(),
                            "idVozac" to document.data?.getValue("idVozac")
                                .toString(),
                            "regKamion" to document.data?.getValue("regKamion")
                                .toString(),
                            "idKamion" to document.data?.getValue("idKamion")
                                .toString(),
                            "regPrikolica" to document.data?.getValue("regPrikolica")
                                .toString(),
                            "idPrikolica" to document.data?.getValue("idPrikolica")
                                .toString(),
                            "redniBroj" to redniBrojGore
                        )
                        korisnikInfo.document(document.data.getValue("idInfo").toString()).set(infoZapis)
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

//Funkcija koja pomiće redni broj iz info vozača dolje
fun FirestoreUpdateInfoDolje(idVozac: String, redoslijed: Int, velicina: Int){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()

    var redniBrojGore: Int
    var redniBrojDolje: Int

    firebaseFirestore.collection("Korisnik").document(idVozac).collection("InfoVozaca")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    Log.d("TAG", document.data?.getValue("redniBroj").toString())
                    var redniBroj: Int = document.data?.getValue("redniBroj").toString().toInt()

                    if (redniBroj == redoslijed+1) {
                        redniBrojGore = redoslijed
                        val korisnikInfo =
                            firebaseFirestore.collection("Korisnik")
                                .document(idVozac)
                                .collection("InfoVozaca")


                        val infoZapis = hashMapOf(
                            "idDodijeljenVozac" to document.data?.getValue(
                                "idDodijeljenVozac"
                            ).toString(),
                            "idInfo" to document.data?.getValue("idInfo")
                                .toString(),
                            "idPoduzeca" to document.data?.getValue(
                                "idPoduzeca"
                            ).toString(),
                            "idVozac" to document.data?.getValue("idVozac")
                                .toString(),
                            "regKamion" to document.data?.getValue("regKamion")
                                .toString(),
                            "idKamion" to document.data?.getValue("idKamion")
                                .toString(),
                            "regPrikolica" to document.data?.getValue("regPrikolica")
                                .toString(),
                            "idPrikolica" to document.data?.getValue("idPrikolica")
                                .toString(),
                            "redniBroj" to redniBrojGore
                        )
                        korisnikInfo.document(document.data.getValue("idInfo").toString()).set(infoZapis)
                    }

                    if (redniBroj == redoslijed && redniBroj < velicina){
                        redniBrojGore = redoslijed + 1
                        val korisnikInfo =
                            firebaseFirestore.collection("Korisnik")
                                .document(idVozac)
                                .collection("InfoVozaca")


                        val infoZapis = hashMapOf(
                            "idDodijeljenVozac" to document.data?.getValue(
                                "idDodijeljenVozac"
                            ).toString(),
                            "idInfo" to document.data?.getValue("idInfo")
                                .toString(),
                            "idPoduzeca" to document.data?.getValue(
                                "idPoduzeca"
                            ).toString(),
                            "idVozac" to document.data?.getValue("idVozac")
                                .toString(),
                            "regKamion" to document.data?.getValue("regKamion")
                                .toString(),
                            "idKamion" to document.data?.getValue("idKamion")
                                .toString(),
                            "regPrikolica" to document.data?.getValue("regPrikolica")
                                .toString(),
                            "idPrikolica" to document.data?.getValue("idPrikolica")
                                .toString(),
                            "redniBroj" to redniBrojGore
                        )
                        korisnikInfo.document(document.data.getValue("idInfo").toString()).set(infoZapis)
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

//Funkcija koja ažurira odabran dogovoren posao
fun FirestoreUpdateDogovoreniPosao(poduzece: Poduzeca){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()
    val poduzeca = firebaseFirestore.collection("DogovoreniPosao")

    val poduzeceZapis = hashMapOf(
        "poduzece" to poduzece.Poduzece,
        "datum" to poduzece.Datum,
        "status" to poduzece.Status,
        "adrsUtovar" to poduzece.AdrsUtovar,
        "adrsIstovar" to poduzece.AdrsIstovar,
        "roba" to poduzece.Roba,
        "brojTura" to poduzece.BrojTura,
        "kontakt" to poduzece.Kontakt,
        "email" to poduzece.Email,
        "id" to poduzece.id
    )
    poduzeca.document(poduzece.id).set(poduzeceZapis)
}

//Funkcija koja briše odabran dogovoren posao
fun FirestoreDeleteDogovoreniPosao(poduzece: Poduzeca){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()

    val id = poduzece.id
    var brojac: Int = 0

    firebaseFirestore.collection("DogovoreniPosao").document(id).collection("DodijeljeniVozaci")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    brojac++
                    if (document.data.getValue("status").toString() == "Kvar"){
                        brojac--
                    }
                }
            }
            if (brojac == 0){
                firebaseFirestore.collection("DogovoreniPosao").document(id).delete().addOnSuccessListener {
                    Log.d("TAG", "Dokument je uspješno obrisan!")
                }.addOnFailureListener{
                        e-> Log.w("TAG", "Brisanje dokumenta nije uspjelo!", e)
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

//Funkcija koja briše dodijeljenog vozača iz FireStore'a te sukladno tome i njegov InfoVozac
fun FirestoreDeleteDodijeljenogVozaca(idDogovoreniPosao: String, dodijeljenVozac: String){
    val firebaseFirestore:FirebaseFirestore = FirebaseFirestore.getInstance()

    var idInfo: String = ""
    var idVozac: String = ""
    var redniBroj: Int = 0
    var redniBrojPromjena: Int = 0

    firebaseFirestore.collection("DogovoreniPosao").document(idDogovoreniPosao).collection("DodijeljeniVozaci")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    if (document.data.getValue("idDodijeljenVozac").toString() == dodijeljenVozac) {
                        idInfo = document.data.getValue("idInfo").toString()
                        idVozac = document.data.getValue("idVozac").toString()

                        firebaseFirestore.collection("DogovoreniPosao").document(idDogovoreniPosao).collection("DodijeljeniVozaci").document(dodijeljenVozac).delete().addOnSuccessListener {
                            Log.d("TAG", "Dokument je uspješno obrisan!")
                        }.addOnFailureListener{
                                e-> Log.w("TAG", "Brisanje dokumenta nije uspjelo!", e)
                        }

                        firebaseFirestore.collection("Korisnik").document(idVozac).collection("InfoVozaca").document(idInfo).get().addOnSuccessListener {
                                document -> if (document!=null){
                                    redniBroj = document.data?.getValue("redniBroj").toString().toInt()
                                    Log.d("TAG", redniBroj.toString())

                            firebaseFirestore.collection("Korisnik").document(idVozac).collection("InfoVozaca").document(idInfo).delete().addOnSuccessListener {
                                Log.d("TAG", "Dokument je uspješno obrisan!")
                            }.addOnFailureListener{
                                    e-> Log.w("TAG", "Brisanje dokumenta nije uspjelo!", e)
                            }

                                    firebaseFirestore.collection("Korisnik").document(idVozac).collection("InfoVozaca")
                                        .get()
                                        .addOnCompleteListener {
                                            if (it.isComplete) {
                                                for (document in it.result!!) {
                                                    Log.d("TAG", document.data?.getValue("redniBroj").toString())

                                                    if (document.data?.getValue("redniBroj").toString().toInt() > redniBroj) {
                                                        redniBrojPromjena = document.data?.getValue("redniBroj").toString().toInt()
                                                        redniBrojPromjena--
                                                        val korisnikInfo =
                                                            firebaseFirestore.collection("Korisnik")
                                                                .document(idVozac)
                                                                .collection("InfoVozaca")


                                                        val infoZapis = hashMapOf(
                                                            "idDodijeljenVozac" to document.data?.getValue(
                                                                "idDodijeljenVozac"
                                                            ).toString(),
                                                            "idInfo" to document.data?.getValue("idInfo")
                                                                .toString(),
                                                            "idPoduzeca" to document.data?.getValue(
                                                                "idPoduzeca"
                                                            ).toString(),
                                                            "idVozac" to document.data?.getValue("idVozac")
                                                                .toString(),
                                                            "regKamion" to document.data?.getValue("regKamion")
                                                                .toString(),
                                                            "idKamion" to document.data?.getValue("idKamion")
                                                                .toString(),
                                                            "regPrikolica" to document.data?.getValue("regPrikolica")
                                                                .toString(),
                                                            "idPrikolica" to document.data?.getValue("idPrikolica")
                                                                .toString(),
                                                            "redniBroj" to redniBrojPromjena
                                                        )
                                                        korisnikInfo.document(document.data.getValue("idInfo").toString()).set(infoZapis)
                                                    }
                                                }
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.w("Tag", "Error getting documents: ", exception)
                                        }

                                }
                        }
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

interface FirestoreCallBackDodijeljeniVozaciObrisi {
    fun onCallback(idInfo: String, idVozac: String)
}

fun FirestoreDohvatiVozilaPrikolice(myCallBack: FirestoreCallBackVozilaPrikolice) {

    var listaPoduzeca = arrayListOf<VoziloPrikolica>()
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var GodinaProizvodnje: String
    var IdVozila: String
    var Ispravan: String
    var NazivModela: String
    var Nosivost: String
    var Registracija: String
    var RegistriranDo: String
    var Slika: String
    var slika: Bitmap? = null
    var Snaga: String
    var VrstaVozila: String


    firebaseFirestore.collection("Vozilo")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {

                var broj= it.result.size()
                var brojac = 0

                for (document in it.result!!) {
                    GodinaProizvodnje = document.data.getValue("godinaProizvodnje").toString()
                    IdVozila = document.data.getValue("idVozila").toString()
                    Ispravan = document.data.getValue("ispravan").toString()
                    NazivModela = document.data.getValue("nazivModela").toString()
                    Nosivost = document.data.getValue("nosivost").toString()
                    Registracija = document.data.getValue("registracija").toString()
                    RegistriranDo = document.data.getValue("registriranDo").toString()
                    Slika = document.data.getValue("slika").toString()
                    Snaga = document.data.getValue("snaga").toString()
                    VrstaVozila = document.data.getValue("vrstaVozila").toString()

                    listaPoduzeca.add(
                        VoziloPrikolica(
                            GodinaProizvodnje,
                            IdVozila,
                            Ispravan,
                            NazivModela,
                            Nosivost,
                            Registracija,
                            RegistriranDo,
                            Slika,
                            slika,
                            Snaga,
                            VrstaVozila
                        )
                    )

                    FirestorDohvatiSlikuVozila(
                        Slika,
                        object : FirestoreCallBackVoziloSlika {
                            override fun onCallback(slika1: Bitmap, slikaURL : String) {
                              slika = slika1
                                for (vozilo in listaPoduzeca){
                                    if (vozilo.slikaURL == slikaURL) vozilo.slika = slika1
                                }
                                brojac++
                                if(brojac == broj) myCallBack.onCallback(listaPoduzeca)
                            }
                        })

                }

            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

fun FirestorDohvatiSlikuVozila(slikaURL: String, myCallBack: FirestoreCallBackVoziloSlika) {

    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()


        val firebaseStorageReference =
            firebaseStorage.getReference().child("vozilo").child(slikaURL)
        firebaseStorageReference.getBytes(1024 * 1024).addOnCompleteListener {
            if (it.isComplete) {

                val bitmap = BitmapFactory.decodeByteArray(it.result, 0, it.result.size)
                myCallBack.onCallback(bitmap, slikaURL)
            }
        }.addOnFailureListener {
            Log.w("Neuspjeh", "Neuspjšeno dohvaćanje slike")
        }

}

interface FirestoreCallBackVoziloSlika {
    fun onCallback(slika: Bitmap, slikaURL: String)
}

fun FirestoreDohvatiPovijestKvarova(myCallBack: FirestoreCallBackPovijestKvarova, idTest: String) {

    var listaKvarovaPovijest = arrayListOf<KvarPovijest>()
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var datumKvara: String
    var datumPopravka: String
    var opisKvara: String
    var opisPopravka: String


    firebaseFirestore.collection("Vozilo").document(idTest).collection("Kvar").whereEqualTo("popravljeno", "Da")
        .get()
        .addOnCompleteListener {
            if (it.isComplete) {
                for (document in it.result!!) {
                    datumKvara = document.data.getValue("datumKvara").toString()
                    datumPopravka = document.data.getValue("datumPopravka").toString()
                    opisKvara = document.data.getValue("opisKvara").toString()
                    opisPopravka = document.data.getValue("opisPopravka").toString()



                    listaKvarovaPovijest.add(
                        KvarPovijest(
                            datumKvara,
                            datumPopravka,
                            opisKvara,
                            opisPopravka,
                        )
                    )
                }
                myCallBack.onCallback(listaKvarovaPovijest)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Tag", "Error getting documents: ", exception)
        }
}

interface FirestoreCallBackPovijestKvarova {
    fun onCallback(lista: ArrayList<KvarPovijest>)
}





