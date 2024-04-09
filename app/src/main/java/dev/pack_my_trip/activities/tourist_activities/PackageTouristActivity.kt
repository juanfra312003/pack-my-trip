package dev.pack_my_trip.activities.tourist_activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.tourist_adapters.ServicesPackageAdapter
import dev.pack_my_trip.databinding.ActivityPackageTouristBinding
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista

class PackageTouristActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPackageTouristBinding
    private lateinit var paquete_turista : PaquetesPorTurista
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el paquete a partir de la actividad anterior.
        paquete_turista = intent.getSerializableExtra("paquete_turista") as PaquetesPorTurista

        // Cargar los valores
        load_values()

        // Manejar la barra de navegación
        manageNavBar()
    }

    @SuppressLint("SetTextI18n")
    private fun load_values(){
        // Cargar los valroes de: nombre, costo, fecha, organizador
        binding.costoEditableTextPackagetourist.text = "$" + paquete_turista.paqueteActual.precio.toString()

        // Obtener la fecha en formato dia/mes/año
        val fechaPaquete = paquete_turista.fecha.date.toString() + "/" + (paquete_turista.fecha.month + 1).toString() + "/" + (paquete_turista.fecha.year + 1900).toString()

        binding.fechaEditableTextPackagetourist.text = fechaPaquete
        binding.organizadorTextEditablePackageT.text = paquete_turista.paqueteActual.nombreOrganizador
        binding.textFieldPackageNameEditable.text = paquete_turista.paqueteActual.nombre

        // Cargar la imagen
        when (paquete_turista.paqueteActual.tipo){
            //TODO: Cambiar las imagenes por las que se encuentran en el proyecto en firebase storage
            "Volcan" -> binding.imageViewPackageType.setImageResource(R.drawable.volcan)
            "Buceo" -> binding.imageViewPackageType.setImageResource(R.drawable.buceo)
            "Aviario" -> binding.imageViewPackageType.setImageResource(R.drawable.aviario)
        }


        // Mostrar los servicios a través del adapter del mismo
        binding.listViewServicesPackage.adapter = ServicesPackageAdapter(this, paquete_turista.paqueteActual.servicios)
    }

    private fun manageNavBar(){
        binding.bottomNavigationViewTourist.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuBack -> {
                    startActivity(Intent(this, DashboardTouristActivity::class.java))
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
}