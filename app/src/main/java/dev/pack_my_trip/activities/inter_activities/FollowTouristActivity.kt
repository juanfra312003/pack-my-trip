package dev.pack_my_trip.activities.inter_activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.tasks.Task
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityFollowTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario

class FollowTouristActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : ActivityFollowTouristBinding
    private lateinit var usuario : Usuario
    private lateinit var paquete : PaqueteTuristico

    // Map attributes
    private lateinit var mMap: GoogleMap

    // Location attributes
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var lastLocation: Location? = null
    private var lastLocationMarker: Marker? = null

    // Lista de polylines para dibujar rutas
    private val currentPolylines = mutableListOf<Polyline>()

    // Gestión del permiso de localización
    private val getPermissionLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startLocationUpdates()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFollowTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        paquete = intent.getSerializableExtra("paquete") as PaqueteTuristico

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapViewFollowTourist) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(true).setMinUpdateIntervalMillis(5000).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                super.onLocationResult(locationResult)
                if (locationResult.lastLocation != null) {
                    val currentLocation = locationResult.lastLocation

                    if (lastLocation == null){
                        lastLocation = currentLocation
                        updateLocationOnMap()
                        mMap.moveCamera(
                            com.google.android.gms.maps.CameraUpdateFactory.newLatLng(
                                com.google.android.gms.maps.model.LatLng(
                                    currentLocation!!.latitude,
                                    currentLocation.longitude
                                )
                            )
                        )
                        mMap.animateCamera(com.google.android.gms.maps.CameraUpdateFactory.zoomTo(15f))
                    } else {
                        if (locationResult.lastLocation!!.distanceTo(lastLocation!!) > 30){
                            lastLocation = locationResult.lastLocation
                            updateLocationOnMap()
                            mMap.moveCamera(
                                com.google.android.gms.maps.CameraUpdateFactory.newLatLng(
                                    com.google.android.gms.maps.model.LatLng(
                                        currentLocation!!.latitude,
                                        currentLocation.longitude
                                    )
                                )
                            )
                        }
                    }
                    //lastLocationMarker?.remove() // Remove only if not null
                }
            }

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
    }

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
                    "La aplicación necesita permisos de localización para funcionar correctamente.",
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
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, locationRequestCode)
            }
            alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
                Toast.makeText(
                    this,
                    "Funcionalidad de seguimiento a servicios no habilitada.",
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

    // Pintar vector cómo icono de mapa:
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!
        vectorDrawable.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    // Manejo de pines de localización
    private fun updateLocationOnMap() {
        if (lastLocation != null) {
            lastLocationMarker?.remove()

            val latLng = LatLng(lastLocation!!.latitude, lastLocation!!.longitude)
            lastLocationMarker =  mMap.addMarker(MarkerOptions().position(latLng).title("Ultima ubicación").icon(
                bitmapDescriptorFromVector(this, R.drawable.userpin)))

            // Enfocar la cámara en la última ubicación
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
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


