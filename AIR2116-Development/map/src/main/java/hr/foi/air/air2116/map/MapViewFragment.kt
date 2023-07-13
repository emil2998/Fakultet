package hr.foi.air.air2116.map


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception
import java.lang.RuntimeException


class MapViewFragment : Fragment() , OnMapReadyCallback {

    private lateinit var map:GoogleMap
    val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        map = googleMap
        getCurrentLocation()
        //onMapReady(googleMap)
        //Toast.makeText(requireContext(),"this is toast message",Toast.LENGTH_SHORT).show()
        //val sydney = LatLng(-34.0, 151.0)
        //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
   }



        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {


            return inflater.inflate(R.layout.fragment_map_view, container, false)


        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            setupLocClient()
            mapFragment?.getMapAsync(callback)



        }

    private lateinit var fusedLocClient: FusedLocationProviderClient

       private fun setupLocClient() {
           fusedLocClient =
               LocationServices.getFusedLocationProviderClient(requireActivity())
       }

    override fun onMapReady(googleMap: GoogleMap) {

   //   map = googleMap //initialise map
    //  getCurrentLocation()

   }




        // prompt the user to grant/deny access
        private fun requestLocPermissions() {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
                REQUEST_LOCATION
            )
        }

        companion object {
        private const val REQUEST_LOCATION =
            1 //request code to identify specific permission request
        private const val TAG = "MapsActivity" // for debugging
    }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            //check if the request code matches the REQUEST_LOCATION
            if (requestCode == REQUEST_LOCATION) {
                //check if grantResults contains PERMISSION_GRANTED.If it does, call getCurrentLocation()
                if (grantResults.size == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    getCurrentLocation()
                } else {
                    //if it doesn`t log an error message
                    Log.e(TAG, "Location permission has been denied")
                }
            }
        }



    fun getCurrentLocation() {


        // Check if the ACCESS_FINE_LOCATION permission was granted before requesting a location
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {

            // call requestLocPermissions() if permission isn't granted
            requestLocPermissions()
        } else {

            fusedLocClient.lastLocation.addOnCompleteListener {
                // lastLocation is a task running in the background
                val location = it.result //obtain location
                Toast.makeText(requireContext(),location.latitude.toString(),Toast.LENGTH_SHORT).show()
                //reference to the database
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val ref: DatabaseReference = database.getReference("test")
                if (location != null) {
                    Toast.makeText(requireContext(),"this is toast message",Toast.LENGTH_SHORT).show()
                    val latLng = LatLng(location.latitude, location.longitude)
                    // create a marker at the exact location
                    map.addMarker(
                        MarkerOptions().position(latLng)
                            .title("You are currently here!")
                    )
                    // create an object that will specify how the camera will be updated
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)

                    map.moveCamera(update)
                    //Save the location data to the database
                    ref.setValue(location)
                } else {
                    // if location is null , log an error message
                    Log.e(TAG, "No location found")
                }


            }
        }
    }

}






