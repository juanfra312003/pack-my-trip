package dev.pack_my_trip.activities.tourist_activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityDetailsPackageTouristBinding
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista

class DetailsPackageTourist : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsPackageTouristBinding
    private lateinit var paqueteTurista : PaquetesPorTurista
    private lateinit var calificationButtons : List<Button>
    private var calification = 0

    // Colores para los botones de calificación
    val whiteColor = Color.parseColor("#FFFFFF")
    val darkBlueColor = Color.parseColor("#283593")
    val lightBlueColor = Color.parseColor("#C2DDF1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPackageTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el paquete a partir de la actividad anterior.
        paqueteTurista = intent.getSerializableExtra("paquete_turista") as PaquetesPorTurista

        // Manejar los botones de calificación
        calificationButtons = listOf(binding.buttonCalification1, binding.buttonCalification2, binding.buttonCalification3, binding.buttonCalification4, binding.buttonCalification5)
        manageCalificationButtons()
    }

    private fun manageCalificationButtons(){
        binding.buttonCalification1.setOnClickListener {
            calification = 1
            paintValues()
        }

        binding.buttonCalification2.setOnClickListener {
            calification = 2
            paintValues()
        }

        binding.buttonCalification3.setOnClickListener {
            calification = 3
            paintValues()
        }

        binding.buttonCalification4.setOnClickListener {
            calification = 4
            paintValues()
        }

        binding.buttonCalification5.setOnClickListener {
            calification = 5
            paintValues()
        }
    }

    private fun paintValues (){
        for (i in 0..<calification){
            calificationButtons[i].setBackgroundColor(darkBlueColor)
            calificationButtons[i].setTextColor(whiteColor)
        }

        for (i in calification until calificationButtons.size){
            calificationButtons[i].setBackgroundColor(lightBlueColor)
            calificationButtons[i].setTextColor(darkBlueColor)
        }
    }
}