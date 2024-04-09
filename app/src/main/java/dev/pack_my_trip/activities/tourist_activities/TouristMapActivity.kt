package dev.pack_my_trip.activities.tourist_activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.Task
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityTouristMapBinding
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista

class TouristMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : ActivityTouristMapBinding
    private lateinit var paquete_turista : PaquetesPorTurista

    // Map attributes
    private lateinit var mMap: GoogleMap

    // Location attributes
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var lastLocation: Location? = null
    private var lastLocationMarker: Marker? = null

    // Gestión del permiso de localización
    private val getPermissionLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startLocationUpdates()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTouristMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el paquete a partir de la actividad anterior.
        paquete_turista = intent.getSerializableExtra("paquete_turista") as PaquetesPorTurista

        // Inicializar el mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapViewSeeServices) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Manejar la barra de navegación
        manageNavBar()

        // Configurar los elementos de localización
        // Location features
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(true).setMinUpdateIntervalMillis(5000).build()
    }

    // Manejo de la barra de navegación
    private fun manageNavBar(){
        binding.bottomNavigationViewTourist.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuBack -> {
                    val intent = Intent(this, PackageTouristActivity::class.java)
                    intent.putExtra("paquete_turista", paquete_turista)
                    startActivity(intent)
                    true
                }
                R.id.menuChat -> {
                    val intent = Intent(this, ChatTouristActivity::class.java)
                    intent.putExtra("paquete_turista", paquete_turista)
                    startActivity(intent)
                    true
                }
                R.id.menuMap -> {
                    val intent = Intent(this, TouristMapActivity::class.java)
                    intent.putExtra("paquete_turista", paquete_turista)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    // Inicialización del mapa
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    // Inicialización de la localización
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        }
    }

    // Detener la actualización de la localización
    private fun stopLocationUpdates() {
        locationClient.removeLocationUpdates(locationCallback)
    }

    // Verificar permisos
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                baseContext, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(
                    baseContext,
                    "You must grant location permission to see the map and receive updates.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            getPermissionLocation.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            startLocationUpdates()
        }
    }

    private val locationRequestCode = 1000

    // Verificar si el permiso de localización está habilitado
    private fun showLocationPermissionDialog() {
        if (!isLocationEnabled()) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Permiso de Ubicación")
            alertDialog.setMessage("Localización no encendida, enciendela para usar la aplicación con sus funcionalidades")
            alertDialog.setPositiveButton("OK") { _, _ ->
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, locationRequestCode)
            }
            alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
                Toast.makeText(
                    this,
                    "Funcionalidad de seguimiento no habilitada.",
                    Toast.LENGTH_LONG
                ).show()
            }
            val alert = alertDialog.create()
            alert.show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == locationRequestCode) {
            if (isLocationEnabled()) {
                showLocationPermissionDialog()
            } else {
                Toast.makeText(this, "Location is still disabled.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun locationSettings() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            checkLocationPermission()
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Show the user a dialog to change location settings
                try {
                    showLocationPermissionDialog()
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        locationSettings()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

}
