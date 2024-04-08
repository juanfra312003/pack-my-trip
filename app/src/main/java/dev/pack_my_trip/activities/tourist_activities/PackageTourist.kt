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

        // Receive the data from the previous activity
        paquete_turista = intent.getSerializableExtra("paquete_turista") as PaquetesPorTurista
        Log.i("PaqueteTurista", paquete_turista.paqueteActual.nombre)

        setContentView(binding.root)


    }
}