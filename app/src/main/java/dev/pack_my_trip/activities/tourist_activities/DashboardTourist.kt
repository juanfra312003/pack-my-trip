package dev.pack_my_trip.activities.tourist_activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetPaquetesUsuario
import dev.pack_my_trip.Presenter.Turista.DashboardTuristaPresenter
import dev.pack_my_trip.databinding.ActivityDashboardTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario

class DashboardTourist : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardTouristBinding
    private lateinit var usuario : Usuario
    private var dashboardTuristaPresenter : DashboardTuristaPresenter = DashboardTuristaPresenter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el turista a partir de la actividad anterior.
        usuario = intent.getSerializableExtra("usuario") as Usuario

        Log.i("DashboardTourist", "Usuario recibido: ${usuario.correo}")

    }

    fun inicializarVariables (){
        dashboardTuristaPresenter.getPaquetesUsuario(usuario.correo, this, object :
            OnGetPaquetesUsuario {
            override fun onGetPaquetesUsuario(paquetes: List<PaqueteTuristico>) {
                usuario.listaPaquetes = paquetes
            }
        })
    }
}