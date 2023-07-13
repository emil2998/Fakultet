package hr.foi.air.air2116.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.air.air2116.R
import hr.foi.air.air2116.StorageActivity
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.core.Korisnik
import hr.foi.air.core.Komunikator
import hr.foi.air.air2116.repository.FirestorDohvatiSlikeKorisnika
import hr.foi.air.air2116.repository.FirestoreCallBack
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.profile_toolbar.*
import java.util.*


class Profile : Fragment(R.layout.activity_profile) {

        private lateinit var komunikator: Komunikator



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        settingsButton.setOnClickListener {
           showPopup(settingsButton)
        }

        val toggle: Switch = view.findViewById(R.id.switchPin)

        val userID = MainActivity.ulogiraniKorisnik.id.toString()

        val prefLoad = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val pin = prefLoad.getString("pin", "empty")

        toggle.isChecked = pin == "da"

        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val prefSave = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val edt = prefSave.edit()
                edt.putString("pin", "da")
                edt.commit()
                edt.putString("korisnik", userID)
                edt.commit()
            } else {
                val prefSave = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val edt = prefSave.edit()
                edt.putString("pin", "ne")
                edt.commit()
            }
        }
    }



    //Popup za settings/edit profila

    private fun showPopup(view: View) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.pop_up_settings)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.header1 -> {
                    //Toast.makeText(this@Profile, item.title, Toast.LENGTH_SHORT).show()
                    changePassword()

                }
                R.id.header2 -> {
                    //Toast.makeText(this@Profile, item.title, Toast.LENGTH_SHORT).show()
                    changeEmail()
                }
                R.id.header3 -> {
                    //Toast.makeText(this@Profile, item.title, Toast.LENGTH_SHORT).show()
                    changePin()
                }
                R.id.header4 -> {
                    changeImePrezime()
                    //Toast.makeText(this@Profile, item.title, Toast.LENGTH_SHORT).show()
                }
                R.id.header5 -> {
                    changePicture()
                    //Toast.makeText(this@Profile, item.title, Toast.LENGTH_SHORT).show()
                }
                R.id.header6 -> {
                    changeMobile()
                    //Toast.makeText(this@Profile, item.title, Toast.LENGTH_SHORT).show()
                }
            }

            true
        })

        popup.show()
    }

    private fun changeImePrezime(){
        val error = arrayOfNulls<String>(1)
        val builder = AlertDialog.Builder(context)
        val textView = TextView(context)
        textView.text = "Promijeni Ime i prezime"
        textView.setPadding(20, 30, 20, 30)
        textView.textSize = 20f
        textView.setBackgroundColor(Color.BLUE)
        textView.setTextColor(Color.BLACK)

        builder.setCustomTitle(textView)
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_change_ime, null, false)
        val changeImeNovoIme =
            viewInflated.findViewById<View>(R.id.changeImeNovoIme) as EditText
        val changePrezimeNovoPrezime =
            viewInflated.findViewById<View>(R.id.changePrezimeNovoPrezime) as EditText

        builder.setView(viewInflated)
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialogInterface, which ->
            //asd
        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, which -> }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newIme = changeImeNovoIme.text.toString()
            val newPrezime = changePrezimeNovoPrezime.text.toString()
            if (newIme != "" && newPrezime != "") {

                    MainActivity.ulogiraniKorisnik.ime=newIme
                    MainActivity.ulogiraniKorisnik.prezime=newPrezime
                    firebaseFirestore.collection("Korisnik")
                        .document(MainActivity.ulogiraniKorisnik.id.toString())
                        .update(
                            "ime", MainActivity.ulogiraniKorisnik.ime ,
                            "prezime", MainActivity.ulogiraniKorisnik.prezime

                        )
                    loadData()
                    dialog.dismiss()
                }

        }

    }

    private fun changePassword(){
        val error = arrayOfNulls<String>(1)
        val builder = AlertDialog.Builder(context)
        val textView = TextView(context)
        textView.text = "Promijeni password"
        textView.setPadding(20, 30, 20, 30)
        textView.textSize = 20f
        textView.setBackgroundColor(Color.BLUE)
        textView.setTextColor(Color.BLACK)

        builder.setCustomTitle(textView)
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_change_password, null, false)
        val changeuserdataNewPassword =
            viewInflated.findViewById<View>(R.id.changeuserdataNewPassword) as EditText
        val changeuserdataNewPasswordRepeat =
            viewInflated.findViewById<View>(R.id.changeuserdataNewPasswordRepeat) as EditText

        builder.setView(viewInflated)
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialogInterface, which ->
            //asd
        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, which -> }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newPass = changeuserdataNewPassword.text.toString()
            val repeatPass = changeuserdataNewPasswordRepeat.text.toString()
            if (newPass != "" && repeatPass != "") {

                if (newPass == repeatPass) {
                    MainActivity.ulogiraniKorisnik.lozinka=newPass
                    firebaseFirestore.collection("Korisnik")
                        .document(MainActivity.ulogiraniKorisnik.id.toString())
                        .update(
                            "lozinka", MainActivity.ulogiraniKorisnik.lozinka
                        )
                    loadData()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun changePin(){
        val error = arrayOfNulls<String>(1)
        val builder = AlertDialog.Builder(context)
        val textView = TextView(context)
        textView.text = "Promijeni PIN"
        textView.setPadding(20, 30, 20, 30)
        textView.textSize = 20f
        textView.setBackgroundColor(Color.BLUE)
        textView.setTextColor(Color.BLACK)

        builder.setCustomTitle(textView)
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_change_pin, null, false)
        val changePinNewPin =
            viewInflated.findViewById<View>(R.id.changePinNewPin) as EditText
        val changePinRepeatPin =
            viewInflated.findViewById<View>(R.id.changePinRepeatPin) as EditText

        builder.setView(viewInflated)
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialogInterface, which ->
            //asd
        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, which -> }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newPin = changePinNewPin.text.toString()
            val repeatPin = changePinRepeatPin.text.toString()
            if (newPin != "" && repeatPin != "") {

                if (newPin == repeatPin) {
                    MainActivity.ulogiraniKorisnik.pin=newPin
                    firebaseFirestore.collection("Korisnik")
                        .document(MainActivity.ulogiraniKorisnik.id.toString())
                        .update(
                            "pin", MainActivity.ulogiraniKorisnik.pin
                        )
                    loadData()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun changeMobile(){
        val error = arrayOfNulls<String>(1)
        val builder = AlertDialog.Builder(context)
        val textView = TextView(context)
        textView.text = "Promijeni broj"
        textView.setPadding(20, 30, 20, 30)
        textView.textSize = 20f
        textView.setBackgroundColor(Color.BLUE)
        textView.setTextColor(Color.BLACK)

        builder.setCustomTitle(textView)
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_change_mobile, null, false)
        val changeMobNewMobb =
            viewInflated.findViewById<View>(R.id.changeMobNewMob) as EditText
        val changeMobRepeatMob =
            viewInflated.findViewById<View>(R.id.changeMobRepeatMob) as EditText

        builder.setView(viewInflated)
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialogInterface, which ->
            //asd
        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, which -> }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newMob = changeMobNewMobb.text.toString()
            val repeatMob = changeMobRepeatMob.text.toString()
            if (newMob != "" && repeatMob != "") {

                if (newMob == repeatMob) {
                    MainActivity.ulogiraniKorisnik.mobitel=newMob
                    firebaseFirestore.collection("Korisnik")
                        .document(MainActivity.ulogiraniKorisnik.id.toString())
                        .update(
                            "mobitel", MainActivity.ulogiraniKorisnik.mobitel
                        )
                    loadData()
                    dialog.dismiss()
                }
            }
        }
    }


    private fun changeEmail(){
        val error = arrayOfNulls<String>(1)
        val builder = AlertDialog.Builder(context)
        val textView = TextView(context)
        textView.text = "Promijeni Email"
        textView.setPadding(20, 30, 20, 30)
        textView.textSize = 20f
        textView.setBackgroundColor(Color.BLUE)
        textView.setTextColor(Color.BLACK)

        builder.setCustomTitle(textView)
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_change_email, null, false)
        val changeEmailNewEmail =
            viewInflated.findViewById<View>(R.id.changeEmailNewEmail) as EditText
        val changeEmailRepeatEmail =
            viewInflated.findViewById<View>(R.id.changeEmailRepeatEmail) as EditText

        builder.setView(viewInflated)
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialogInterface, which ->
            //asd
        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, which -> }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newEmail = changeEmailNewEmail.text.toString()
            val repeatEmail = changeEmailRepeatEmail.text.toString()
            if (newEmail != "" && repeatEmail != "") {

                if (newEmail == repeatEmail) {

                    MainActivity.ulogiraniKorisnik.email=newEmail
                    firebaseFirestore.collection("Korisnik")
                        .document(MainActivity.ulogiraniKorisnik.id.toString())
                        .update(
                            "email", MainActivity.ulogiraniKorisnik.email
                        )
                    loadData()
                    dialog.dismiss()
                }
            }
        }
    }




    private fun changePicture(){
        val builder = AlertDialog.Builder(context)
        val textView = TextView(context)
        textView.text = "Promijeni sliku"
        textView.setPadding(20, 30, 20, 30)
        textView.textSize = 20f
        textView.setBackgroundColor(Color.BLUE)
        textView.setTextColor(Color.BLACK)

        builder.setCustomTitle(textView)
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_change_picture, null, false)
        val uploadPictureButton =
            viewInflated.findViewById<View>(R.id.uploadSlike) as Button

        uploadPictureButton.setOnClickListener {
            val intent = Intent(activity, StorageActivity::class.java)
            startActivity(intent)
        }

        builder.setView(viewInflated)
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialogInterface, which ->
            //asd
        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, which -> }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val listaUlogiraniKorisnik = mutableListOf<Korisnik>()
            listaUlogiraniKorisnik.add(
                Korisnik(
                MainActivity.ulogiraniKorisnik.korisnickoIme.toString(),
                MainActivity.ulogiraniKorisnik.lozinka.toString(),
                MainActivity.ulogiraniKorisnik.ime.toString(),
                MainActivity.ulogiraniKorisnik.prezime.toString(),
                MainActivity.ulogiraniKorisnik.korisnickoIme.toString(),
                MainActivity.ulogiraniKorisnik.pin.toString(),
                MainActivity.ulogiraniKorisnik.id.toString(),
                MainActivity.ulogiraniKorisnik.slikaURL.toString(),
                null,
                MainActivity.ulogiraniKorisnik.mobitel.toString(),
                MainActivity.ulogiraniKorisnik.email.toString(),
                MainActivity.ulogiraniKorisnik.datumRodenja as Date
            )
            )
            FirestorDohvatiSlikeKorisnika(
                listaUlogiraniKorisnik,
                object : FirestoreCallBack {
                    override fun onCallback(lista: MutableList<Korisnik>) {
                        for (korisnik0 in lista) {
                            MainActivity.ulogiraniKorisnik.slika = korisnik0.slika
                        }
                        loadData()
                        dialog.dismiss()
                    }
                })
        }
    }

    // Access a Cloud Firestore instance from your Activity

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()



    public fun loadData(){
                first_name.text = MainActivity.ulogiraniKorisnik.ime.toString()
                last_name.text = MainActivity.ulogiraniKorisnik.prezime.toString()
                email.text = MainActivity.ulogiraniKorisnik.email.toString()
                mobitel.text = MainActivity.ulogiraniKorisnik.mobitel.toString()
                birth.text = MainActivity.ulogiraniKorisnik.datumRodenja?.year.toString() + '/' +
                             MainActivity.ulogiraniKorisnik.datumRodenja?.month.toString() + '/' +
                             MainActivity.ulogiraniKorisnik.datumRodenja?.day.toString()
                slikaProfila.setImageBitmap(MainActivity.ulogiraniKorisnik.slika)
    }




}