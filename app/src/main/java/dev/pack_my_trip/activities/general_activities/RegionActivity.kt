package dev.pack_my_trip.activities.general_activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.support.annotation.RequiresApi
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
import com.google.android.gms.tasks.Task
import dev.pack_my_trip.ConectionBack.Interfaces.OnEditarRegionUsuario
import dev.pack_my_trip.Presenter.General.EditarRegionUsuarioPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.inter_activities.DashboardInter
import dev.pack_my_trip.activities.operator_activities.DashboardOperator
import dev.pack_my_trip.activities.tourist_activities.DashboardTouristActivity
import dev.pack_my_trip.databinding.ActivityRegionBinding
import dev.pack_my_trip.models.data_model.Usuario

class RegionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegionBinding
    private var regionPresenter : EditarRegionUsuarioPresenter = EditarRegionUsuarioPresenter()
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var lat = 0.0F
    private var lon = 0.0F
    private lateinit var usuario : Usuario

    @androidx.annotation.RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir usuario
        usuario = intent.getSerializableExtra("usuario") as Usuario

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(true).setMinUpdateIntervalMillis(5000).build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                lat = locationResult.lastLocation!!.latitude.toFloat()
                lon = locationResult.lastLocation!!.longitude.toFloat()
            }
        }
        manageButtons()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageButtons() {
        binding.buttonSaveRegionSelected.setOnClickListener {
            if (verificarSpinners() == 0) {
                Toast.makeText(this, "Seleccione solo una región", Toast.LENGTH_SHORT).show()
            } else {
                when (verificarSpinners()) {
                    1 -> {
                        usuario.region = binding.spinnerRegionLocalizacion.selectedItem.toString()
                    }
                    2 -> {
                        usuario.region = binding.spinnerRegionProvincia.selectedItem.toString()
                    }
                    3 -> {
                        usuario.region = binding.spinnerRegionZona.selectedItem.toString()
                    }
                }
                usuario.latitud = lat
                usuario.longitud = lon
                subirUsuario(usuario)
            }
        }
    }

    private fun verificarSpinners() : Int {
        val spinnerRegion = binding.spinnerRegionLocalizacion.selectedItem.toString()
        val spinnerProvincia = binding.spinnerRegionProvincia.selectedItem.toString()
        val spinnerZona = binding.spinnerRegionZona.selectedItem.toString()

        if(spinnerRegion != "" && spinnerProvincia == "" && spinnerZona == ""){
            return 1
        }
        if(spinnerRegion == "" && spinnerProvincia != "" && spinnerZona == ""){
            return 2
        }
        if(spinnerRegion == " " && spinnerProvincia == " " && spinnerZona != ""){
            return 3
        }
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun subirUsuario(usuario : Usuario) {
        regionPresenter.editarRegionUsuario(usuario, this, object : OnEditarRegionUsuario {
            override fun onEditarRegionUsuario(realizado: Boolean) {
                if(realizado){
                    // Actualización exitosa
                } else {
                    Toast.makeText(this@RegionActivity, "Error al actualizar la región", Toast.LENGTH_SHORT).show()
                }
            }
        })
        when (usuario.tipo) {
            'T' -> {
                val intent = Intent(baseContext, DashboardTouristActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
                finish()
            }
            'O' -> {
                val intent = Intent(baseContext, DashboardOperator::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
                finish()
            }
            'I' -> {
                val intent = Intent(baseContext, DashboardInter::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
                finish()
            }
        }
    }

    // Gestión del permiso de localización
    private val getPermissionLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startLocationUpdates()
            }
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

    override fun onResume() {
        super.onResume()
        locationSettings()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        locationClient.removeLocationUpdates(locationCallback)
    }

}