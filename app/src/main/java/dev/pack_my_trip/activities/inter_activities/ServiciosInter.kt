package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityPaqueteTuristicoCreadoBinding
import dev.pack_my_trip.databinding.ActivityServiciosInterBinding

class ServiciosInter : AppCompatActivity() {
    private lateinit var binding : ActivityServiciosInterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiciosInterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicializarVariables()
    }

    fun inicializarVariables(){
    }
}