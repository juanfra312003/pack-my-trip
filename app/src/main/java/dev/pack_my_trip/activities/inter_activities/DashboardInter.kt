package dev.pack_my_trip.activities.inter_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetPaquetes
import dev.pack_my_trip.Presenter.Intermediario.DashboardInterPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.general_activities.ProfileViewActivity
import dev.pack_my_trip.activities.general_activities.RegionActivity
import dev.pack_my_trip.adapters.Intermediario.DashboardInterAdapter
import dev.pack_my_trip.databinding.ActivityDashboardInterBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario

class DashboardInter : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardInterBinding
    private lateinit var imagenUsuario: ImageView
    private lateinit var buttonProfileDashInt: ImageButton
    private lateinit var buttonRegionDashInt: ImageButton
    private lateinit var listViewDashBoardIntermediario: ListView
    private lateinit var buttonCrearPaqueteTuristicoInt: Button
    private lateinit var buttonVerPaquetesAgenda: Button
    private lateinit var usuario: Usuario
    private lateinit var dashboardInterAdapter: DashboardInterAdapter
    private lateinit var dashboardInterPresenter: DashboardInterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardInterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        dashboardInterPresenter = DashboardInterPresenter()
        inicializarComponentes()
        eventoProfile()
        eventoRegion()
        eventoCrearPaquete()
        eventoVerPaquetesAgenda()
    }

    fun inicializarComponentes(){
        imagenUsuario = findViewById(R.id.imagenProfileUserDash)
        buttonProfileDashInt = findViewById(R.id.buttonProfileDashInt)
        buttonRegionDashInt = findViewById(R.id.buttonRegionDashInt)
        listViewDashBoardIntermediario = findViewById(R.id.listViewDashBoardIntermediario)
        buttonCrearPaqueteTuristicoInt = findViewById(R.id.buttonCrearPaqueteTuristicoInt)
        buttonVerPaquetesAgenda = findViewById(R.id.buttonVerPaquetesAgenda)
        dashboardInterPresenter.getPaquetes(usuario.correo, this, object: OnGetPaquetes {
            override fun onGetPaquetes(paquetes: List<PaqueteTuristico>) {
                dashboardInterAdapter = DashboardInterAdapter(this@DashboardInter, paquetes)
                listViewDashBoardIntermediario.adapter = dashboardInterAdapter
            }
        })
    }

    fun eventoProfile(){
        buttonProfileDashInt.setOnClickListener{
            val intent = Intent(this, ProfileViewActivity::class.java)
            intent.putExtra("usuario", DashboardInter::class.java)
        }
    }

    fun eventoRegion(){
        buttonRegionDashInt.setOnClickListener{
            val intent = Intent(this, RegionActivity::class.java)
            intent.putExtra("usuario", DashboardInter::class.java)
            startActivity(intent)
        }
    }

    fun eventoCrearPaquete(){
        buttonCrearPaqueteTuristicoInt.setOnClickListener{
            val intent = Intent(this, RegionActivity::class.java)
            intent.putExtra("usuario", DashboardInter::class.java)
            startActivity(intent)
        }
    }

    fun eventoVerPaquetesAgenda(){
        buttonVerPaquetesAgenda.setOnClickListener{
            val intent = Intent(this, AgendaIntermediarioActivity::class.java)
            intent.putExtra("usuario", DashboardInter::class.java)
            startActivity(intent)
        }
    }
}