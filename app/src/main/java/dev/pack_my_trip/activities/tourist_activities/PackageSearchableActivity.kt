package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.squareup.picasso.Picasso
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServiciosPaquete
import dev.pack_my_trip.ConectionBack.Interfaces.OnRegistrarPaqueteUsuario
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetServiciosPaquetePresenter
import dev.pack_my_trip.Presenter.Turista.PackageSearchPresenter
import dev.pack_my_trip.Presenter.Turista.RegistrarPaqueteUsuarioPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.tourist_adapters.ServicesPackageAdapter
import dev.pack_my_trip.databinding.ActivityPackageSearchableBinding
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio

import java.util.Date

class PackageSearchableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPackageSearchableBinding
    private lateinit var turista: Usuario
    private lateinit var paquete: PaqueteTuristico
    private var packageSearchPresenter : PackageSearchPresenter = PackageSearchPresenter()
    private var registrarPaqueteUsuarioPresenter : RegistrarPaqueteUsuarioPresenter = RegistrarPaqueteUsuarioPresenter()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageSearchableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        turista = intent.getSerializableExtra("usuario") as Usuario
        paquete = intent.getSerializableExtra("paquete_nuevo") as PaqueteTuristico

        loadValues()
        manageButtons()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadValues() {
        binding.organizadorTextEditablePackageTSearch.text = paquete.correoIntermediario
        binding.textFieldPackageNameEditableSearch.text = paquete.nombre
        binding.costoEditableTextPackagetouristSearch.text = "$" + paquete.precioDolares.toString()
        if (!paquete.imagen.isNullOrBlank()) {
            Picasso.get().load(paquete.imagen).into(binding.imageViewPackageTypeSearch)
        } else {
            binding.imageViewPackageTypeSearch.setImageResource(R.drawable.paquete_imagen)
        }
        loadListView()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadListView(){
        packageSearchPresenter.getServiciosPaquete(paquete.id, this, object : OnGetServiciosPaquete {
            override fun onGetServiciosPaquete(servicios: List<Servicio>) {
                binding.listViewServicesSearchPackage.adapter = ServicesPackageAdapter(this@PackageSearchableActivity, servicios.toMutableList())
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageButtons() {
        binding.buttonProgramPackage.setOnClickListener {
            registrarUsuarioPaquete()
            val intent = Intent(this, DashboardTouristActivity::class.java)
            intent.putExtra("usuario", turista)
            startActivity(intent)
        }

        binding.backButtonSearchPackage.setOnClickListener {
            val intent = Intent(this, SearchPackagesActivity::class.java)
            intent.putExtra("turista", turista)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun registrarUsuarioPaquete(){
        registrarPaqueteUsuarioPresenter.registrarPaqueteUsuario(turista.correo, paquete.id, this, object : OnRegistrarPaqueteUsuario {
            override fun onRegistrarPaqueteUsuario(registrado: Boolean) {
                if (registrado){
                    Toast.makeText(this@PackageSearchableActivity, "Paquete registrado con éxito", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@PackageSearchableActivity, "Error al registrar el paquete", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}

