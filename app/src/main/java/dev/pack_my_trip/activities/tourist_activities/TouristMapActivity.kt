package dev.pack_my_trip.activities.tourist_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityTouristMapBinding

class TouristMapActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTouristMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTouristMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}