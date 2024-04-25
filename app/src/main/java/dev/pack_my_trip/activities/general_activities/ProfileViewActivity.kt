package dev.pack_my_trip.activities.general_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.models.models_tourist.Turista

class ProfileViewActivity : AppCompatActivity() {

    private lateinit var turista : Turista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityProfileViewBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        // Recibir el turista a partir de la actividad anterior.
        turista = intent.getSerializableExtra("turista") as Turista
    }
}