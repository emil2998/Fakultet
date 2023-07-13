package hr.foi.air.login

import android.content.Context
import androidx.fragment.app.Fragment
import hr.foi.air.core.LoginPresenter

class PinPresenter : LoginPresenter {
    override fun getModuleName(context: Context?): String? {
        return "PIN"
    }

    override fun getFragment(): Fragment {
        return PrijavaPin()
    }
}