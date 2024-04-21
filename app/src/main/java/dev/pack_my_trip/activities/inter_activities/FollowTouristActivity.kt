package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityFollowTouristBinding

class FollowTouristActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFollowTouristBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}