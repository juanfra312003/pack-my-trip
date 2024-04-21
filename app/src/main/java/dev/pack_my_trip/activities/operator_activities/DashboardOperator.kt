package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import dev.pack_my_trip.ConectionBack.OnGetServicios
import dev.pack_my_trip.Presenter.DashboardOperatorPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivityDashboardOperatorBinding
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario

class DashboardOperator : AppCompatActivity() {
    private lateinit var misServiciosListView: ListView
    private lateinit var registrarServicioBtn: Button
    private lateinit var usuario: Usuario
    private var dashboardOperatorPresenter: DashboardOperatorPresenter = DashboardOperatorPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_operator)
        usuario = Usuario("operador@gmail.com","a","a",'A')
        inicializarVariables()
        eventoRegistarServicio()
    }

    fun inicializarVariables(){
        misServiciosListView = findViewById(R.id.MisServiciosListView)
        dashboardOperatorPresenter.getServicios(usuario.correo, this, object:OnGetServicios{
            override fun onGetServicios(servicios: List<Servicio>) {
                val data = mutableMapOf<String,ByteArray>()
                for(servicio in servicios){
                    val texto = servicio.nombre
                    val byteImg = servicio.portada
                    data[texto] = byteImg.toByteArray(Charsets.UTF_8)
                }
                val adapter = MisServiciosAdapter(this@DashboardOperator, data, servicios)
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
}