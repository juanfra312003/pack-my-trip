package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetPaquetesUsuario
import dev.pack_my_trip.Presenter.Turista.DashboardTuristaPresenter
import dev.pack_my_trip.activities.general_activities.ProfileViewActivity
import dev.pack_my_trip.activities.general_activities.RegionActivity
import dev.pack_my_trip.adapters.tourist_adapters.PackagesTouristAdapter
import dev.pack_my_trip.databinding.ActivityDashboardTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario

class DashboardTouristActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardTouristBinding
    private lateinit var usuario : Usuario
    private var dashboardTuristaPresenter : DashboardTuristaPresenter = DashboardTuristaPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario

        // Llamado a funciones de inicialización
        inicializarVariables()
        personalizarLayout()
        manageButtons()


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
                binding.listTouristPackages.adapter = PackagesTouristAdapter(this@DashboardTouristActivity, paquetes.toMutableList())
                binding.listTouristPackages.setOnItemClickListener { _, _, position, _ ->
                    val paqueteTurista = usuario.listaPaquetes[position]
                    val intent = Intent(baseContext, PackageTouristActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                }
            }
        })

        // Cargar la imagen de perfil
        val urlImg : String? = usuario.fotoPerfil
        if(urlImg != null && !urlImg.isEmpty()){
            Picasso.get().load(urlImg).into(binding.dashboardTouristPhoto)
        }
    }

    private fun personalizarLayout(){
        binding.textoBienvenido.text = "Bienvenido de nuevo, ${usuario.usuario} !"
    }

    private fun manageButtons(){
       binding.buttonProfile.setOnClickListener {
           startActivity(Intent(this, ProfileViewActivity::class.java).putExtra("usuario", usuario))
       }

        binding.buttonLocation.setOnClickListener {
            startActivity(Intent(this, RegionActivity::class.java).putExtra("usuario", usuario))
        }
    }
}