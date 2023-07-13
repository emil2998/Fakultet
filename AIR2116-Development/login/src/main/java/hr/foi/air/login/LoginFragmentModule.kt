
package hr.foi.air.login
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import kotlin.concurrent.thread
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hr.foi.air.core.Komunikator


class LoginFragmentModule  : Fragment(R.layout.modul_login) {
    private lateinit var komunikator: Komunikator


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = view.findViewById(R.id.username) as EditText;
        val passWord = view.findViewById(R.id.password) as EditText;
        val loginBtn = view.findViewById(R.id.loginBtn) as Button;


        loginBtn.setOnClickListener { // for testing purpose we are using username as gfg
            // and password as geeksforgeeks.
            // On successful login it will display a toast message
            //Toast.makeText(getActivity(), "Stiska", Toast.LENGTH_SHORT).show()ž
            komunikator = activity as Komunikator
            komunikator.otvoriDogovorenePoslove()
        }


    }
}