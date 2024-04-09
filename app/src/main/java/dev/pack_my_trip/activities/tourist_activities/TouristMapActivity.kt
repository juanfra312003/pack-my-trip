package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityTouristMapBinding
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista

class TouristMapActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTouristMapBinding
    private lateinit var paquete_turista : PaquetesPorTurista
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTouristMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el paquete a partir de la actividad anterior.
        paquete_turista = intent.getSerializableExtra("paquete_turista") as PaquetesPorTurista

        // Manejar la barra de navegación
        manageNavBar()
    }
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
}