package hr.foi.air.air2116

import android.content.Context
import androidx.fragment.app.Fragment
import hr.foi.air.air2116.fragment.Prijava
import hr.foi.air.core.LoginPresenter

class PrijavaPresenter : LoginPresenter {
    override fun getModuleName(context: Context?): String? {
        return "Prijava"
    }

    override fun getFragment(): Fragment {
        return Prijava()
    }
}