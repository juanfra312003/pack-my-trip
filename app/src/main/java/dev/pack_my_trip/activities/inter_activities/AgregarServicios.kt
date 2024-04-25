package dev.pack_my_trip.activities.inter_activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServicios
import dev.pack_my_trip.Presenter.Intermediario.AgregarServiciosPresenter
import dev.pack_my_trip.activities.general_activities.RegionActivity
import dev.pack_my_trip.activities.tourist_activities.PackageSearchableActivity
import dev.pack_my_trip.adapters.Intermediario.AgregarServiciosAdapter
import dev.pack_my_trip.adapters.tourist_adapters.PackagesSearchAdapter
import dev.pack_my_trip.databinding.ActivityAgregarServiciosBinding
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import java.util.Locale

class AgregarServicios : AppCompatActivity() {
    private lateinit var binding : ActivityAgregarServiciosBinding
    private lateinit var intermediario: Usuario
    private lateinit var servicios: MutableList<Servicio>
    private lateinit var agregarServiciosPresenter: AgregarServiciosPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarServiciosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Recibir el turista a partir de la actividad anterior.
        intermediario = intent.getSerializableExtra("intermedario") as Usuario

        // Manejo de botones
        inicializarComponentes()
        eventoBackButtonServicesDetails()
        eventoBackButtonChangeRegionSearchPackages()
        binding.listViewServicesSearchTourist.setOnItemClickListener { _, _, position, _ ->
            val paqueteNuevo = servicios[position]
            val intent = Intent(baseContext, PackageSearchableActivity::class.java)
            intent.putExtra("servicios", paqueteNuevo)
            intent.putExtra("turista", intermediario)
            startActivity(intent)
        }
        manageFilterButton()
    }

    private fun inicializarComponentes() {
        agregarServiciosPresenter.getServicios(this, object: OnGetServicios{
            override fun onGetServicios(servicios: List<Servicio>) {
                this@AgregarServicios.servicios = servicios.toMutableList()
                binding.listViewServicesSearchTourist.adapter = AgregarServiciosAdapter(this@AgregarServicios, this@AgregarServicios.servicios)
            }
        })
    }

    private fun eventoBackButtonServicesDetails() {
        binding.backButtonPackagesDetails.setOnClickListener {
            val intent = Intent(this, DashboardInter::class.java)
            startActivity(intent)
        }
    }

    private fun eventoBackButtonChangeRegionSearchPackages() {
        binding.buttonChangeRegionSearchPackages.setOnClickListener {
            val intent = Intent(this, RegionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun manageFilterButton (){
        binding.buttonSearchFiltrePackages.setOnClickListener {
            val text = binding.editableTextSearch.text.toString()
            if (text.isEmpty()) {
                // Mostrar todos los paquetes turísticos.
                binding.listViewServicesSearchTourist.adapter = AgregarServiciosAdapter(this, servicios)
            }
            else{
                // Filtrar los paquetes turísticos con las letras en lower case.
                val serviciosFiltrados = servicios.filter { it.nombre.lowercase(Locale.ROOT).contains(
                    text.lowercase(Locale.ROOT)
                ) }
                binding.listViewServicesSearchTourist.adapter = AgregarServiciosAdapter(this, serviciosFiltrados.toMutableList())
            }
        }
    }
}