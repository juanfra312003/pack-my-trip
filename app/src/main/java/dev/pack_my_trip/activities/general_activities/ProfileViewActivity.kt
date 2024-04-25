package dev.pack_my_trip.activities.general_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.databinding.ActivityProfileViewBinding
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.Turista

class ProfileViewActivity : AppCompatActivity() {

    private lateinit var usuario: Usuario
    private lateinit var binding: ActivityProfileViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el turista a partir de la actividad anterior.
        usuario = intent.getSerializableExtra("usuario") as Usuario
    }
}