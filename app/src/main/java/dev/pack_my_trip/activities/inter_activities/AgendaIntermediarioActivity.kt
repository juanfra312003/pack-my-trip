package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityAgendaIntermediarioBinding

class AgendaIntermediarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgendaIntermediarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendaIntermediarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}