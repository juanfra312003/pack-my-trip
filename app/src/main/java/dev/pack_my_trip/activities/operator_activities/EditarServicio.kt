package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import dev.pack_my_trip.R

class EditarServicio : AppCompatActivity() {
    private lateinit var aceptarCambiosBtn: Button
    private lateinit var caracteristicasETxt: EditText
    private lateinit var nombreServicioETxt : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_servicio)
        inicializarVariables()
        eventoRegistarServicio()
    }

    fun inicializarVariables(){
        aceptarCambiosBtn = findViewById(R.id.aceptarCambiosBtn)
        caracteristicasETxt = findViewById(R.id.caracteristicasEdtETxt)
        caracteristicasETxt.setSelection(0)
        caracteristicasETxt.gravity = Gravity.START or Gravity.TOP
        nombreServicioETxt = findViewById(R.id.nombreServicioEdtETxt)
        nombreServicioETxt.isEnabled = false
        nombreServicioETxt.setText("Experiencia Acuática Playa San Carlos.")
    }

    fun eventoRegistarServicio(){
        aceptarCambiosBtn.setOnClickListener{
            val intent = Intent(this, DashboardOperator::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            startActivity(intent) //Inicia la actividad
        }
    }
}