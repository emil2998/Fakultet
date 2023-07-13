package hr.foi.air.air2116.interfaces

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment

interface LoginModuleInterface {
    fun getName():String
    fun getAutor(): String
    fun getBtnLabel(): String

    fun startAuthentication(username: String): View

    fun otvoriModulPin()


    fun getModuleName(context: Context?): String?
    fun getFragment(): Fragment?


}