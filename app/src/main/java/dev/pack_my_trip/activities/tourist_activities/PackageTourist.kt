package dev.pack_my_trip.activities.tourist_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityPackageTouristBinding

class PackageTourist : AppCompatActivity() {

    private lateinit var binding : ActivityPackageTouristBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageTouristBinding.inflate(layoutInflater)


        setContentView(binding.root)


    }
}