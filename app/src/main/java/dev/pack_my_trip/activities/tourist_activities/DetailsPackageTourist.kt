package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.general_activities.ChatActivity
import dev.pack_my_trip.databinding.ActivityDetailsPackageTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista

class DetailsPackageTourist : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsPackageTouristBinding
    private lateinit var paqueteTurista : PaqueteTuristico
    private lateinit var usuario : Usuario
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

        paqueteTurista = intent.getSerializableExtra("paquete_turista") as PaqueteTuristico
        usuario = intent.getSerializableExtra("usuario") as Usuario
        calificationButtons = listOf(binding.buttonCalification1, binding.buttonCalification2, binding.buttonCalification3, binding.buttonCalification4, binding.buttonCalification5)
        manageButtons()
    }

    private fun manageButtons (){
        manageCalificationButtons()
        manageNavBar()
        manageOtherButtons()
    }

    private fun manageOtherButtons (){
        binding.buttonSendComments.setOnClickListener {
            paqueteTurista.comentarios = binding.comentariosEditableText.text.toString()
            paqueteTurista.calificacion = calification
            binding.comentariosEditableText.text.clear()
            calification = 0
            paintValues()

            // Dar el mensaje de que se ha enviado la calificación
            Toast.makeText(baseContext, "Calificación y comentarios enviados", Toast.LENGTH_SHORT).show()
        }

        binding.buttonDownloadFactura.setOnClickListener {
            val intent = Intent(this, FacturaPaqueteTuristaActivity::class.java)
            intent.putExtra("paquete_turista", paqueteTurista)
            startActivity(intent)
        }
    }

    private fun manageNavBar(){
        binding.bottomNavigationViewTourist.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuBack -> {
                    val intent = Intent(this, PackageTouristActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                    true
                }
                R.id.menuChat -> {
                    val intent = Intent(this, ChatActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                    true
                }
                R.id.menuMap -> {
                    val intent = Intent(this, TouristMapActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
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