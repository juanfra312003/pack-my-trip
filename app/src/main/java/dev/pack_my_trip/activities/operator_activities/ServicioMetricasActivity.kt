package dev.pack_my_trip.activities.operator_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetMetricas
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityMetricasBinding
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario

class ServicioMetricasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMetricasBinding
    private lateinit var misServiciosListView: ListView
    private var usuario: Usuario? = null
    private lateinit var servicios: List<Servicio>
    private var repository: Repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMetricasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        inicializarVariables()
    }

    fun inicializarVariables(){
        misServiciosListView = binding.MisServiciosListView
        repository.getMetricas(usuario!!.correo, this, object: OnGetMetricas {
            override fun onGetMetricas(servicios: List<Servicio>) {
                this@ServicioMetricasActivity.servicios = servicios
                misServiciosListView.adapter = ServiciosMetricasAdapter(this@ServicioMetricasActivity, this@ServicioMetricasActivity.servicios)
            }
        })
    }
}