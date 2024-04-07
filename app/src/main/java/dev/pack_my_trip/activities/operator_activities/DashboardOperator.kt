package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityDashboardOperatorBinding

class DashboardOperator : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardOperatorBinding
    private lateinit var misServiciosListView: ListView
    private lateinit var registrarServicioBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardOperatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicializarVariables()
        eventoRegistarServicio()
    }

    fun inicializarVariables(){
        misServiciosListView = findViewById(R.id.MisServiciosListView)
        val data = mapOf("a" to 1, "b" to 2)
        val adapter = MisServiciosAdapter(this, data)
        misServiciosListView.adapter = adapter
        registrarServicioBtn = findViewById(R.id.registrarServicioDashBtn)
    }

    fun eventoRegistarServicio(){
        registrarServicioBtn.setOnClickListener{
            val intent = Intent(this, RegistroServicios::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            startActivity(intent) //Inicia la actividad
        }
    }
}