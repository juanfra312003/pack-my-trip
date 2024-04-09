package dev.pack_my_trip.activities.tourist_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.databinding.ActivityPackageSearchableBinding
import dev.pack_my_trip.models.models_tourist.PaqueteTuristico
import dev.pack_my_trip.models.models_tourist.Turista

class PackageSearchableActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPackageSearchableBinding
    private lateinit var turista : Turista
    private lateinit var paquete : PaqueteTuristico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageSearchableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener turista y paquete turistico
        val turista = intent.getSerializableExtra("turista")
        val paquete = intent.getSerializableExtra("paquete_nuevo")

    }
}