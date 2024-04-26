package dev.pack_my_trip.activities.tourist_activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.annotation.RequiresApi
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetMetricas
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.operator_activities.ServiciosMetricasAdapter
import dev.pack_my_trip.databinding.ActivityMetricasBinding
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario

class MetricasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMetricasBinding
    private lateinit var misServiciosListView: ListView
    private lateinit var misServicios2ListView: ListView
    private lateinit var usuario: Usuario
    private lateinit var servicios: List<Servicio>
    private var repository: Repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMetricasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicializarVariables()
        usuario = intent.getSerializableExtra("usuario") as Usuario
    }

    fun inicializarVariables(){
        misServiciosListView = findViewById(R.id.MisServiciosListView)
        repository.getMetricas(usuario.correo, this, object: OnGetMetricas{
            override fun onGetMetricas(servicios: List<Servicio>) {
                this@MetricasActivity.servicios = servicios
                misServiciosListView.adapter = ServiciosMetricasAdapter(this@MetricasActivity, this@MetricasActivity.servicios)
            }
        })
    }
}