package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import dev.pack_my_trip.R

class RegistroServicios : AppCompatActivity() {
    private lateinit var registrarServicioBtn: Button
    private lateinit var caracteristicasETxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_servicios)
        inicializarVariables()
        eventoRegistarServicio()
    }

    fun inicializarVariables(){
        registrarServicioBtn = findViewById(R.id.registrarServicioRegBtn)
        caracteristicasETxt = findViewById(R.id.caracteristicasRegETxt)
        caracteristicasETxt.setSelection(0)
        caracteristicasETxt.gravity = Gravity.START or Gravity.TOP
    }

    fun eventoRegistarServicio(){
        registrarServicioBtn.setOnClickListener{
            val intent = Intent(this, DashboardOperator::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            startActivity(intent) //Inicia la actividad
        }
    }
}