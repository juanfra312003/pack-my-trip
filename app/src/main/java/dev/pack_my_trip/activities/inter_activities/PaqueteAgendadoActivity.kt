package dev.pack_my_trip.activities.inter_activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.squareup.picasso.Picasso
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServiciosPaquete
import dev.pack_my_trip.Presenter.Turista.PackageSearchPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.tourist_adapters.ServicesPackageAdapter
import dev.pack_my_trip.databinding.ActivityPaqueteAgendadoBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import kotlin.random.Random

class PaqueteAgendadoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPaqueteAgendadoBinding
    private lateinit var usuario : Usuario
    private lateinit var paquete : PaqueteTuristico
    private var packageSearchPresenter = PackageSearchPresenter()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaqueteAgendadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        paquete = intent.getSerializableExtra("paquete") as PaqueteTuristico

        cargarValores()
        eventoVolver()
        eventoMapa()
        eventoChat()
        eventoDetalles()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarValores (){
        val lista = listOf(
            "Juan Francisco",
            "Julian",
            "Pedro",
            "Maria",
            "Jose",
            "Luis",
            "Ana",
            "Sofia",
            "Carlos",
            "Fernando",
            "Ricardo"
        )

        binding.organizadorTextEditablePaqueteAgendado.text = lista[Random.nextInt(0, lista.size)]
        binding.textFieldPackageNameEditableAgenda.text = paquete.nombre
        binding.fechaEditableTextPaqueteAgenda.text = paquete.fechaHora
        binding.costoEditableTextPackageAgenda.text = paquete.precioDolares.toString()

        if (!paquete.imagen.isNullOrBlank()) {
            Picasso.get().load(paquete.imagen).into(binding.imageViewPackageTypeAgenda)
        } else {
            binding.imageViewPackageTypeAgenda.setImageResource(R.drawable.paquete_imagen)
        }

        packageSearchPresenter.getServiciosPaquete(paquete.id, baseContext, object :
            OnGetServiciosPaquete {
            override fun onGetServiciosPaquete(servicios: List<Servicio>) {
                binding.listViewServicesPackageAgenda.adapter = ServicesPackageAdapter(baseContext, servicios.toMutableList())
            }
        })
    }

    private fun eventoVolver(){
        binding.buttonInfoAditionalPackage.setOnClickListener {
            val intent = Intent(baseContext, AgendaIntermediarioActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }

    private fun eventoMapa(){
        binding.bottomNavigationViewTourist.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuMap -> {
                    val intent = Intent(baseContext, FollowTouristActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun eventoChat(){
        binding.bottomNavigationViewTourist.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuChat -> {
                    val intent = Intent(baseContext, ChatIntermediarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun eventoDetalles(){
        binding.bottomNavigationViewTourist.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuBack -> {
                    val intent = Intent(baseContext, AgendaIntermediarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}