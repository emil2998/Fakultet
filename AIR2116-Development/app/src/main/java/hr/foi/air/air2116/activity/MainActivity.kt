package hr.foi.air.air2116.activity

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hr.foi.air.air2116.PrijavaPresenter
import hr.foi.air.air2116.fragment.Profile
import hr.foi.air.air2116.fragment.glavniIzbornik
import hr.foi.air.air2116.R
import hr.foi.air.air2116.fragment.*
import hr.foi.air.air2116.fragment.OpisZavrsenogPosla
import hr.foi.air.core.*
import hr.foi.air.login.PinPresenter
import hr.foi.air.air2116.map.MapViewFragment
import java.util.*

class MainActivity : AppCompatActivity(), Komunikator {

    object ulogiraniKorisnik {
        var korisnickoIme: String? = null
        var ime: String? = null
        var prezime: String? = null
        var uloga: String? = null
        var slika: Bitmap? = null
        var mobitel: String? = null
        var email: String? = null
        var datumRodenja: Date? = null
        var id: String? = null
        var pin: String? = null
        var lozinka: String? = null
        var slikaURL: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zaslon)

        //Firebase autentifikacija - anonymous (potrebno zbog dohvaćanja slike iz baze)
        var auth: FirebaseAuth
        auth = Firebase.auth
        super.onStart()
        val currentUser = auth.currentUser
        auth.signInAnonymously().addOnCompleteListener {
            val user = auth.currentUser
        }

        //Postavi početni fragment prijave
        val prefLoad = getPreferences(Context.MODE_PRIVATE)
        val pin = prefLoad.getString("pin", "empty")
        val userID = prefLoad.getString("korisnik", "empty")
        var presenter: LoginPresenter = PrijavaPresenter()
        if (pin == "da")
            presenter = PinPresenter()

        val prijavaFragment = presenter.getFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, prijavaFragment)
            //addToBackStack()
            commit()
        }
    }

    override fun ulogirajKorisnika(korisnik: Korisnik) {
        //Zapiši korisnika u aplikaciju
        ulogiraniKorisnik.korisnickoIme = korisnik.korisnickoIme
        ulogiraniKorisnik.ime = korisnik.ime
        ulogiraniKorisnik.prezime = korisnik.prezime
        ulogiraniKorisnik.uloga = korisnik.uloga
        ulogiraniKorisnik.slika = korisnik.slika
        ulogiraniKorisnik.mobitel = korisnik.mobitel
        ulogiraniKorisnik.email = korisnik.email
        ulogiraniKorisnik.datumRodenja = korisnik.datumRodenja
        ulogiraniKorisnik.id = korisnik.id
        Log.w("KORISNIK", ulogiraniKorisnik.slika.toString())
        //Provjeri ulogu korisnika i postavi fragment
        val glavniIzbornik = glavniIzbornik()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, glavniIzbornik)
            addToBackStack(null)
            commit()
        }

        when (ulogiraniKorisnik.uloga) {
            "Administrator" -> {
                val administrator = administrator()
                supportFragmentManager.beginTransaction().replace(R.id.gumbi, administrator)
                    .commit()
            }
            "Mehaničar" -> {
                val mehanicar = mehanicar()
                supportFragmentManager.beginTransaction().replace(R.id.gumbi, mehanicar).commit()
            }
            "Vozač" -> {
                val vozac = vozac()
                supportFragmentManager.beginTransaction().replace(R.id.gumbi, vozac).commit()
            }
        }

    }
    override fun vratiGlavniMeni(){
        val glavniIzbornik = glavniIzbornik()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, glavniIzbornik)
            addToBackStack(null)
            commit()
        }
    }

    override fun otvoriGlavniMeni() {
        when (ulogiraniKorisnik.uloga) {
            "Administrator" -> {
                val administrator = administrator()
                supportFragmentManager.beginTransaction().replace(R.id.gumbi, administrator)
                    .commit()
            }
            "Mehaničar" -> {
                val mehanicar = mehanicar()
                supportFragmentManager.beginTransaction().replace(R.id.gumbi, mehanicar).commit()
            }
            "Vozač" -> {
                val vozac = vozac()
                supportFragmentManager.beginTransaction().replace(R.id.gumbi, vozac).commit()
            }
        }
    }

    override fun aktivniPoslovi() {
        val aktivniPoslovi = AktivniPoslovi()
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, aktivniPoslovi)
            .addToBackStack(null).commit()

    }

    override fun trenutniPrijevoz() {
        val trenutniPrijevoz = TrenutniPrijevoz()
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, trenutniPrijevoz)
            .addToBackStack(null).commit()
    }


    override fun otvoriOpisPosla(popravak: Popravak) {
        val opis = OpisZavrsenogPosla()
        var bundle: Bundle = Bundle()
        bundle.putString("datumKvara",popravak.datumKvara)
        bundle.putString("datumPopravka",popravak.datumPopravka)
        bundle.putString("hitnost",popravak.hitnost)
        bundle.putString("idKvar",popravak.idKvar)
        bundle.putString("idVozilo",popravak.idVozilo)
        bundle.putString("opisKvara",popravak.opisKvara)
        bundle.putString("opisPopravka",popravak.opisPopravka)
        bundle.putString("popravljeno",popravak.popravljeno)
        opis.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.flContainer, opis)
            .addToBackStack(null).commit()
    }

    override fun podnesiKvar(prijevoz: Prijevozi) {


        val podnesiKvar = PodnesiKvar()

        var bundle: Bundle = Bundle()
        bundle.putString("idPrikolica", prijevoz.idPrikolica)
        bundle.putString("idKamion", prijevoz.idKamion)
        podnesiKvar.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.flContainer, podnesiKvar)
            .addToBackStack(null).commit()


    }

    override fun otvoriMapu() {
        val otvaranjeMape = MapViewFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, otvaranjeMape)
            addToBackStack(null)
            commit()
        }
    }


    override fun otvoriUpravljanjeKorisnicima() {
        val upravljanjeKorisnicima = UpravljanjeKorisnicima()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, upravljanjeKorisnicima)
            addToBackStack(null)
            commit()
        }
    }

    override fun otvoriUpravljanjeVozilima() {
        val upravljanjeVozilima=UpravljanjeVozilima()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, upravljanjeVozilima)
            addToBackStack(null)
            commit()
        }
    }

    override fun odjaviSe() {
        ulogiraniKorisnik.datumRodenja = null
        ulogiraniKorisnik.email = null
        ulogiraniKorisnik.mobitel = null
        ulogiraniKorisnik.slika = null
        ulogiraniKorisnik.korisnickoIme = null
        ulogiraniKorisnik.prezime = null
        ulogiraniKorisnik.ime = null
        ulogiraniKorisnik.uloga = null

        //Postavi početni fragment prijave
        val prefLoad = getPreferences(Context.MODE_PRIVATE)
        val pin = prefLoad.getString("pin", "empty")
        val userID = prefLoad.getString("korisnik", "empty")
        var presenter: LoginPresenter = PrijavaPresenter()
        if (pin == "da")
            presenter = PinPresenter()

        val prijavaFragment = presenter.getFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, prijavaFragment)
            //addToBackStack()
            commit()
        }

    }

    override fun proslijediOdabranPosao(idDogovorenPosao: String, adrsUtovar: String, adrsIstovar: String, brojTura: String) {
        val dodjelaVozacaFragment = DodjelaVozacaFragment()

        var bundle: Bundle = Bundle()
        bundle.putString("idDogovoreniPosao", idDogovorenPosao)
        bundle.putString("adrsUtovar", adrsUtovar)
        bundle.putString("adrsIstovar", adrsIstovar)
        bundle.putString("brojTura", brojTura)

        dodjelaVozacaFragment.arguments = bundle

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, dodjelaVozacaFragment)
            addToBackStack(null)
            commit() }
    }

    override fun otvoriInfo(position: Int, idVozaca: String) {
        val infoVozacaFragment = InfoVozacaFragment()

        var bundle: Bundle = Bundle()
        bundle.putInt("pozicija", position)
        bundle.putString("idVozaca", idVozaca)

        infoVozacaFragment.arguments = bundle

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, infoVozacaFragment)
            addToBackStack(null)
            commit() }
    }

    override fun otvoriDogovorenePoslove() {
        val dogovoreniPosloviFragment = DogovoreniPosloviFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, dogovoreniPosloviFragment)
            addToBackStack(null)
            commit() }
    }

    override fun otvoriProfil() {
        val profilFragment = Profile()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, profilFragment)
            addToBackStack(null)
            commit() }
    }

    override fun otvoriPovijest() {
        val povijestPrijevoza = PovijestPrijevoza()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, povijestPrijevoza)
            addToBackStack(null)
            commit() }
    }

    override fun otvoriPrijavaLozinka() {
        val prijava = Prijava()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, prijava)
            addToBackStack(null)
            commit()
        }
    }
    override fun otvoriVozilaPrikolice(){

        val prikolicaVozilo = OdabirVozilaPrikolica()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, prikolicaVozilo)
            addToBackStack(null)
            commit() }

    }

    override fun otvoriPovijestKvarova(id: String){

        val povijestKvarova = PovijestKvarova()

        var bundle: Bundle = Bundle()
        bundle.putString("id", id)

        povijestKvarova.arguments = bundle

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, povijestKvarova)
            addToBackStack(null)
            commit() }

    }


}