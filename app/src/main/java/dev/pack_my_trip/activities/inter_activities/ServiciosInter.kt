package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import dev.pack_my_trip.R

class ServiciosInter : AppCompatActivity() {
    private lateinit var imageViewPackageType: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios_inter)
    }
}