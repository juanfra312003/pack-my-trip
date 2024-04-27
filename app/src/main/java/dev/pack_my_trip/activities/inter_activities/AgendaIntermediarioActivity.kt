package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetAgenda
import dev.pack_my_trip.Presenter.Intermediario.AgendaPaquetesPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.Intermediario.AdapterAgendaInter
import dev.pack_my_trip.databinding.ActivityAgendaIntermediarioBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario

class AgendaIntermediarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgendaIntermediarioBinding
    private lateinit var usuario : Usuario
    private var agendaPaquetesPresenter = AgendaPaquetesPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendaIntermediarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        load_values()
    }

    private fun load_values(){
        agendaPaquetesPresenter.getPaquetes(usuario.correo, baseContext, object : OnGetAgenda {
            override fun onGetAgenda(paquetes: List<PaqueteTuristico>) {
                binding.listViewPaquetesAgendadosIntermediario.adapter = AdapterAgendaInter(baseContext, paquetes.toMutableList())

            }
        })
    }
}