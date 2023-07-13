package hr.foi.air.core

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.DocumentSnapshot

import com.google.firebase.storage.FirebaseStorage
import java.util.*
import com.google.firebase.firestore.FirebaseFirestore

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import hr.foi.air.core.Korisnik
import hr.foi.air.core.Prijevozi

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

fun FirestorDohvatiSlikeKorisnika(listaKorisnika: List<Korisnik>, myCallBack: FirestoreCallBack) {
    val listaKorisnikaSaSlikom = mutableListOf<Korisnik>()
    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    for (korisnik in listaKorisnika) {
        val firebaseStorageReference =
            firebaseStorage.getReference().child("korisnik").child(korisnik.slikaURL)
        firebaseStorageReference.getBytes(1024 * 1024).addOnCompleteListener {
            if (it.isComplete) {
                val bitmap = BitmapFactory.decodeByteArray(it.result, 0, it.result!!.size)
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