package dev.pack_my_trip.activities.general_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityRegionBinding

class RegionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}