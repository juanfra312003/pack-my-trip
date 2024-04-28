package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import dev.pack_my_trip.ConectionBack.Interfaces.OnActualizarComentariosCalificaciones
import dev.pack_my_trip.Presenter.Turista.ActualizarComentariosCalificacionesPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityDetailsPackageTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario

class DetailsPackageTourist : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsPackageTouristBinding
    private lateinit var paqueteTurista : PaqueteTuristico
    private lateinit var usuario : Usuario
    private lateinit var calificationButtons : List<Button>
    private var calification = 0
    private var actualizarComentariosCalificacionesPresenter : ActualizarComentariosCalificacionesPresenter = ActualizarComentariosCalificacionesPresenter()

    // Colores para los botones de calificación
    val whiteColor = Color.parseColor("#FFFFFF")
    val darkBlueColor = Color.parseColor("#283593")
    val lightBlueColor = Color.parseColor("#C2DDF1")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPackageTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        paqueteTurista = intent.getSerializableExtra("paquete_turista") as PaqueteTuristico
        usuario = intent.getSerializableExtra("usuario") as Usuario
        calificationButtons = listOf(binding.buttonCalification1, binding.buttonCalification2, binding.buttonCalification3, binding.buttonCalification4, binding.buttonCalification5)
        manageButtons()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageButtons (){
        manageCalificationButtons()
        manageNavBar()
        manageOtherButtons()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageOtherButtons (){
        binding.buttonSendComments.setOnClickListener {
            val comentarios = binding.comentariosEditableText.text.toString()
            val calificacionUpload = calification
            registrarActualizacion(comentarios, calificacionUpload)
            // Reiniciar valores
            paqueteTurista.comentarios = binding.comentariosEditableText.text.toString()
            paqueteTurista.calificacion = calification
            binding.comentariosEditableText.text.clear()
            calification = 0
            paintValues()

        }

        binding.buttonDownloadFactura.setOnClickListener {
            Toast.makeText(baseContext, "Factura descargada y enviada al correo.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DashboardTouristActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registrarActualizacion(comentarios : String, calificacion : Int){
        actualizarComentariosCalificacionesPresenter.actualizarComentariosCalificaciones(paqueteTurista.id, usuario.correo, comentarios, calificacion, this, object : OnActualizarComentariosCalificaciones {
            override fun onActualizarComentariosCalificaciones(actualizado: Boolean) {
                if (actualizado){
                    Toast.makeText(baseContext, "Comentarios y calificación actualizados", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(baseContext, "Error al actualizar comentarios y calificación", Toast.LENGTH_SHORT).show()
                }
            }
        })
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
                    val intent = Intent(this, ChatTouristActivity::class.java)
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