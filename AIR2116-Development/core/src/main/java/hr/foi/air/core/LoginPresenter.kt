package hr.foi.air.core

import android.content.Context
import androidx.fragment.app.Fragment

interface LoginPresenter {
    fun getModuleName(context: Context?): String?
    fun getFragment(): Fragment
}