package hr.foi.air.air2116

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.databinding.ActivityStorageBinding
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import android.R
import androidx.fragment.app.FragmentManager


class StorageActivity : AppCompatActivity() {

    lateinit var binding : ActivityStorageBinding
    lateinit var ImageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectImageBtn.setOnClickListener {

            selectImage()

        }

        binding.uploadImageBtn.setOnClickListener {

            uploadImage()

        }
    }


    private fun uploadImage() {

        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading file...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = UUID.randomUUID().toString()
        val storageReference = FirebaseStorage.getInstance().getReference("korisnik/$fileName")

        storageReference.putFile(ImageUri).
                addOnSuccessListener {

                    binding.firebaseImage.setImageURI(null)
                    Toast.makeText(this@StorageActivity, "Uspjesno uploadano", Toast.LENGTH_SHORT).show()
                    if(progressDialog.isShowing) progressDialog.dismiss()

                    firebaseFirestore.collection("Korisnik")
                        .document(MainActivity.ulogiraniKorisnik.id.toString())
                        .update(
                            "slikaURL", fileName
                        )
                    MainActivity.ulogiraniKorisnik.slikaURL = fileName
                    finish()
                }.addOnFailureListener{

                    if (progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(this@StorageActivity, "Neuspjesno", Toast.LENGTH_SHORT).show()
        }


    }

    private fun selectImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK ){

            ImageUri = data?.data!!
            binding.firebaseImage.setImageURI(ImageUri)

        }

    }
}