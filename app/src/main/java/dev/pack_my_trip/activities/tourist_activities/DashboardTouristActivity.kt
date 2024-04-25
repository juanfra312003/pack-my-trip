package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetPaquetesUsuario
import dev.pack_my_trip.Presenter.Turista.DashboardTuristaPresenter
import dev.pack_my_trip.adapters.tourist_adapters.PackagesTouristAdapter
import dev.pack_my_trip.databinding.ActivityDashboardTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista
import dev.pack_my_trip.models.models_tourist.ServicioTuristico
import dev.pack_my_trip.models.models_tourist.Turista

class DashboardTouristActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardTouristBinding
    private lateinit var usuario : Usuario
    private var dashboardTuristaPresenter : DashboardTuristaPresenter = DashboardTuristaPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el turista a partir de la actividad anterior.
        usuario = intent.getSerializableExtra("usuario") as Usuario

        // Llamado a funciones de inicialización
        inicializarVariables()
        personalizarLayout()
        manejoListView()


        /*
        // MOSTRAR LA LISTA DE PAQUETES
        binding.listTouristPackages.adapter = PackagesTouristAdapter(this, turista.paquetes)
        binding.listTouristPackages.setOnItemClickListener { _, _, position, _ ->
            val paqueteTurista = turista.paquetes[position]

            val intent = Intent(baseContext, PackageTouristActivity::class.java)
            intent.putExtra("paquete_turista", paqueteTurista)
            startActivity(intent)
        }


        // MANEJO DE BOTÓN DE BÚSQUEDA DE PAQUETES CON EL TURISTA CÓMO PARÁMETRO PARA LA ACTIVIDAD DE BÚSQUEDA DE PAQUETES
        manageButtons(turista)

         */
    }

    private fun inicializarVariables (){
        dashboardTuristaPresenter.getPaquetesUsuario(usuario.correo, this, object :
            OnGetPaquetesUsuario {
            override fun onGetPaquetesUsuario(paquetes: List<PaqueteTuristico>) {
                usuario.listaPaquetes = paquetes
            }
        })
    }

    private fun personalizarLayout(){
        binding.textoBienvenido.text = "Bienvenido de nuevo, ${usuario.usuario} !"
    }

    private fun manejoListView(){
        if (usuario.listaPaquetes.isNullOrEmpty()){
            Toast.makeText(this, "No tienes paquetes asignados", Toast.LENGTH_SHORT).show()
        }
        else{
            binding.listTouristPackages.adapter = PackagesTouristAdapter(this@DashboardTouristActivity, usuario.listaPaquetes.toMutableList())
        }
    }


    private fun manageButtons(turista : Turista){
        binding.buttonSearchPackages.setOnClickListener {
            val intent = Intent(this, SearchPackagesActivity::class.java)
            intent.putExtra("turista", turista)
            startActivity(intent)
        }

        binding.buttonProfile.setOnClickListener {
            binding.buttonProfile.setOnClickListener {
                val intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
            }
        }

        binding.buttonLocation.setOnClickListener {
            val intent = Intent(this, SelectorActivity::class.java)
            startActivity(intent)
        }
    }
}
