package dev.pack_my_trip.activities.tourist_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityPackageTouristBinding
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista

class PackageTourist : AppCompatActivity() {

    private lateinit var binding : ActivityPackageTouristBinding
    private lateinit var paquete_turista : PaquetesPorTurista
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el paquete a partir de la actividad anterior.
        paquete_turista = intent.getSerializableExtra("paquete_turista") as PaquetesPorTurista
        Log.i("turista", paquete_turista.paqueteActual.nombre)

        // Cargar los valores
        load_values()


    }

    private fun load_values(){
        // Cargar los valroes de: nombre, costo, fecha, organizador
        binding.costoEditableTextPackagetourist.text = paquete_turista.paqueteActual.precio.toString()
        binding.fechaEditableTextPackagetourist.text = paquete_turista.fecha.toString()
        binding.organizadorTextEditablePackageT.text = paquete_turista.paqueteActual.nombreOrganizador
        binding.textFieldPackageNameEditable.text = paquete_turista.paqueteActual.nombre

        // Cargar la imagen
        val imageName = paquete_turista.paqueteActual.tipo
        val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
        binding.imageViewPackageType.setImageResource(resourceId)


        // Mostrar los servicios

    }
}