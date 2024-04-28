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
import kotlin.math.*
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityFollowTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario
import kotlin.random.Random

class FollowTouristActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : ActivityFollowTouristBinding
    private lateinit var usuario : Usuario
    private lateinit var paquete : PaqueteTuristico
    private lateinit var nombre : String

    // Map attributes
    private lateinit var mMap: GoogleMap

    // Location attributes
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var lastLocation: Location? = null
    private var lastLocationMarker: Marker? = null
    private lateinit var latLngTurista : LatLng

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
        nombre = intent.getStringExtra("nombre").toString()
        binding.nombreTuristaSiguiendo.text = nombre

        val latitud = 8f + Random.nextFloat() * (11f - 8f)
        val longitud = -82f + Random.nextFloat() * (-87f + 82f)
        latLngTurista = LatLng(latitud.toDouble(), longitud.toDouble())

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
                        // Llamar a la función para dibujar la ruta aquí o cualquier otra operación que requiera mMap
                        drawRoute()
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
                            // Llamar a la función para dibujar la ruta aquí o cualquier otra operación que requiera mMap
                            drawRoute()
                        }
                    }
                    //lastLocationMarker?.remove() // Remove only if not null
                }
            }

        }

        eventoMapa()
        eventoChat()
        eventoDetalles()
        manejoBarraNavegacion()
    }

    private fun manejoBarraNavegacion(){
        binding.bottomNavigationViewTourist.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuChat -> {
                    val intent = Intent(baseContext, ChatIntermediarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    intent.putExtra("nombre", nombre)
                    startActivity(intent)
                    true
                }
                R.id.menuMap -> {
                    val intent = Intent(baseContext, FollowTouristActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    intent.putExtra("nombre", nombre)
                    startActivity(intent)
                    true
                }
                R.id.menuBack -> {
                    val intent = Intent(baseContext, AgendaIntermediarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun drawRoute() {
        val polyline = mMap.addPolyline(
            com.google.android.gms.maps.model.PolylineOptions()
                .add(
                    com.google.android.gms.maps.model.LatLng(
                        lastLocation!!.latitude,
                        lastLocation!!.longitude
                    ),
                    latLngTurista
                )
        )
        currentPolylines.add(polyline)
        val distancia = calcularDistancia(
            lastLocation!!.latitude,
            lastLocation!!.longitude,
            latLngTurista.latitude,
            latLngTurista.longitude).toInt()

        binding.distanciaKMFollowTourist.text = "Distancia: $distancia km"
    }

    fun calcularDistancia(latitud1: Double, longitud1: Double, latitud2: Double, longitud2: Double): Double {
        val radioTierra = 6371 // Radio de la Tierra en kilómetros
        val latitudEnRadianes1 = Math.toRadians(latitud1)
        val latitudEnRadianes2 = Math.toRadians(latitud2)
        val diferenciaLatitud = Math.toRadians(latitud2 - latitud1)
        val diferenciaLongitud = Math.toRadians(longitud2 - longitud1)

        val a = sin(diferenciaLatitud / 2) * sin(diferenciaLatitud / 2) +
                cos(latitudEnRadianes1) * cos(latitudEnRadianes2) *
                sin(diferenciaLongitud / 2) * sin(diferenciaLongitud / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return radioTierra * c // Distancia en kilómetros
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        // Agregar el marcador aquí, después de que mMap haya sido inicializado
        mMap.addMarker(
            MarkerOptions()
                .position(latLngTurista)
                .title("Turista")
                .icon(bitmapDescriptorFromVector(this, R.drawable.userpin))
        )


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

    private fun eventoMapa(){
        binding.bottomNavigationViewTourist.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuMap -> {
                    val intent = Intent(baseContext, FollowTouristActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    intent.putExtra("nombre", nombre)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun eventoChat(){
        binding.bottomNavigationViewTourist.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuChat -> {
                    val intent = Intent(baseContext, ChatIntermediarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    intent.putExtra("nombre", nombre)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun eventoDetalles(){
        binding.bottomNavigationViewTourist.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuBack -> {
                    val intent = Intent(baseContext, AgendaIntermediarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

}


