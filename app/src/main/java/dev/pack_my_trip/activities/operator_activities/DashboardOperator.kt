package dev.pack_my_trip.activities.operator_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.databinding.ActivityDashboardOperatorBinding

class DashboardOperator : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardOperatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardOperatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}