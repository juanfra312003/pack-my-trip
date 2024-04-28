package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityChatIntermediarioBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario

class ChatIntermediarioActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatIntermediarioBinding
    private lateinit var usuario : Usuario
    private lateinit var paquete : PaqueteTuristico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatIntermediarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuario = intent.getSerializableExtra("usuario") as Usuario
        paquete = intent.getSerializableExtra("paquete") as PaqueteTuristico
    }
}