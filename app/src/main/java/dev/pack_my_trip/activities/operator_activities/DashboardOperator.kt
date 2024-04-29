package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.squareup.picasso.Picasso
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServicios
import dev.pack_my_trip.Presenter.Operador.DashboardOperatorPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.tourist_activities.MetricasActivity
import dev.pack_my_trip.adapters.Operador.MisServiciosAdapter
import dev.pack_my_trip.databinding.ActivityDashboardOperatorBinding
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario

class DashboardOperator : AppCompatActivity() {
    private lateinit var misServiciosListView: ListView
    private lateinit var registrarServicioBtn: Button
    private lateinit var usuario: Usuario
    private lateinit var metricasBtn: Button
    private var dashboardOperatorPresenter: DashboardOperatorPresenter = DashboardOperatorPresenter()
    private lateinit var binding: ActivityDashboardOperatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardOperatorBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_dashboard_operator)
        usuario = intent.getSerializableExtra("usuario") as Usuario


        // Cargar la imagen de perfil
        val urlImg : String? = usuario.fotoPerfil
        if(urlImg != null && !urlImg.isEmpty()){
            Picasso.get().load(urlImg).into(binding.operadorPhotoDash)
        }
        else{
            binding.operadorPhotoDash.setImageResource(R.drawable.usuario_profile)
        }
        binding.textoBienvenido.text = "Bienvenido de nuevo, ${usuario.usuario} !"


        inicializarVariables()
        eventoRegistarServicio()
        eventoMetricasBtn()
    }

    fun inicializarVariables(){
        metricasBtn = findViewById(R.id.verMetricasBtn)
        misServiciosListView = findViewById(R.id.MisServiciosListView)
        dashboardOperatorPresenter.getServicios(usuario.correo, this, object: OnGetServicios {
            override fun onGetServicios(servicios: List<Servicio>) {
                val adapter = MisServiciosAdapter(this@DashboardOperator, servicios, usuario)
                misServiciosListView.adapter = adapter
            }
        })
        registrarServicioBtn = findViewById(R.id.registrarServicioDashBtn)


    }

    fun eventoRegistarServicio(){
        registrarServicioBtn.setOnClickListener{
            val intent = Intent(this, RegistroServicios::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            intent.putExtra("usuario", usuario)
            startActivity(intent) //Inicia la actividad
        }
    }

    fun eventoMetricasBtn(){
        metricasBtn.setOnClickListener{
            val intent = Intent(this, ServicioMetricasActivity::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            intent.putExtra("usuario", usuario)
            startActivity(intent) //Inicia la actividad
        }
    }
}