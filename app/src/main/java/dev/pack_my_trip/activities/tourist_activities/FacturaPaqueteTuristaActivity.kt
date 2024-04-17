package dev.pack_my_trip.activities.tourist_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityFacturaPaqueteTuristaBinding
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista

class FacturaPaqueteTuristaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFacturaPaqueteTuristaBinding
    private lateinit var paqueteTurista : PaquetesPorTurista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacturaPaqueteTuristaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el paquete por turista a partir de la actividad anterior.
        paqueteTurista = intent.getSerializableExtra("paquete_turista") as PaquetesPorTurista
    }
}