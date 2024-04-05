package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityDashboardInterBinding

class DashboardInter : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardInterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardInterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}