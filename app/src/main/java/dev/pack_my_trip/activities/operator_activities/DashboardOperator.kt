package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServicios
import dev.pack_my_trip.Presenter.Operador.DashboardOperatorPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.tourist_activities.MetricasActivity
import dev.pack_my_trip.adapters.Operador.MisServiciosAdapter
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario

class DashboardOperator : AppCompatActivity() {
    private lateinit var misServiciosListView: ListView
    private lateinit var registrarServicioBtn: Button
    private lateinit var usuario: Usuario
    private lateinit var metricasBtn: Button
    //private lateinit var adapter: MisServiciosAdapter
    private var dashboardOperatorPresenter: DashboardOperatorPresenter = DashboardOperatorPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_operator)
        usuario = intent.getSerializableExtra("usuario") as Usuario

        inicializarVariables()
        eventoRegistarServicio()
        eventoMetricasBtn()
    }

    fun inicializarVariables(){
        metricasBtn = findViewById(R.id.verMetricasBtn)
        misServiciosListView = findViewById(R.id.MisServiciosListView)
        dashboardOperatorPresenter.getServicios(usuario.correo, this, object: OnGetServicios {
            override fun onGetServicios(servicios: List<Servicio>) {
                //adapter = MisServiciosAdapter(this@DashboardOperator, servicios, usuario)
                misServiciosListView.adapter = MisServiciosAdapter(this@DashboardOperator, servicios, usuario)
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
        /*metricasBtn.setOnClickListener{
            val intent = Intent(this, MetricasActivity::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            intent.putExtra("usuario", usuario)
            intent.putExtra("servicios", ArrayList(adapter.data))
            startActivity(intent) //Inicia la actividad
        }*/
    }
}