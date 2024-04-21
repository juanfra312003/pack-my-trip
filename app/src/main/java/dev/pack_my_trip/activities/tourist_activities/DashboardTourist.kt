package dev.pack_my_trip.activities.tourist_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.databinding.ActivityDashboardTouristBinding

class DashboardTourist : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardTouristBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}