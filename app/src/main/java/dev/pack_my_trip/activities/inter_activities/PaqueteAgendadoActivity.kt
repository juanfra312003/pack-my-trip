package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityPaqueteAgendadoBinding

class PaqueteAgendadoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPaqueteAgendadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaqueteAgendadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}