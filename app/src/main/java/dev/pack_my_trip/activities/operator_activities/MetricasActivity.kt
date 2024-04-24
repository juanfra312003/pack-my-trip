package dev.pack_my_trip.activities.tourist_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.Operador.ServiciosMetricasAdapter
import dev.pack_my_trip.databinding.ActivityMetricasBinding

class MetricasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMetricasBinding
    private lateinit var misServiciosListView: ListView
    private lateinit var misServicios2ListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMetricasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicializarVariables()
    }

    fun inicializarVariables(){
        misServiciosListView = findViewById(R.id.MisServiciosListView)
        val data = mapOf("a" to 1, "b" to 2)
        val adapter = ServiciosMetricasAdapter(this, data)
        misServiciosListView.adapter = adapter

        misServicios2ListView = findViewById(R.id.MisServiciosListView)
        val data2 = mapOf("a" to 1, "b" to 2)
        val adapter2 = ServiciosMetricasAdapter(this, data2)
        misServiciosListView.adapter = adapter2
    }
}