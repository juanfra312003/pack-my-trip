package dev.pack_my_trip.activities.inter_activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetAllServicios
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
    private lateinit var serviciosAgregados: MutableList<Servicio>
    private lateinit var agregarServiciosPresenter: AgregarServiciosPresenter
    private lateinit var agregarServiciosAdapter: AgregarServiciosAdapter
    private var nombrePaquete: String = ""
    private var fechaPaquete: String = ""
    private var costoPaquete: Float = 0.0f
    private var id: Int = 0
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var urlImg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarServiciosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        agregarServiciosPresenter = AgregarServiciosPresenter()

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
        agregarServiciosEvento()
    }

    private fun inicializarComponentes() {
        // Recibir el turista a partir de la actividad anterior.
        intermediario = intent.getSerializableExtra("usuario") as Usuario
        nombrePaquete = intent.getSerializableExtra("nombrePaquete") as String
        fechaPaquete = intent.getSerializableExtra("fecha") as String
        costoPaquete = intent.getSerializableExtra("costo") as Float
        day = intent.getSerializableExtra("dia") as Int
        month = intent.getSerializableExtra("mes") as Int
        year = intent.getSerializableExtra("year") as Int
        serviciosAgregados = intent.getSerializableExtra("servicios") as MutableList<Servicio>
        urlImg = intent.getSerializableExtra("urlImagen") as String?
        agregarServiciosPresenter.getAllServicios(this, object: OnGetAllServicios {
            override fun onGetAllServicios(servicios: List<Servicio>) {
                if(servicios == null) {
                    this@AgregarServicios.servicios = listOf<Servicio>().toMutableList()
                    Toast.makeText(this@AgregarServicios, "No hay servicios disponibles", Toast.LENGTH_SHORT).show()
                }
                else{
                    this@AgregarServicios.servicios = servicios.toMutableList()
                    this@AgregarServicios.servicios.removeAll(this@AgregarServicios.serviciosAgregados)
                }
                agregarServiciosAdapter = AgregarServiciosAdapter(this@AgregarServicios, this@AgregarServicios.servicios)
                binding.listViewServicesSearchTourist.adapter = agregarServiciosAdapter
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

    private fun agregarServiciosEvento(){
        binding.agregarServiciosBtn.setOnClickListener{
            val editar =intent.getSerializableExtra("editar") as Boolean?
            var intent = Intent(this@AgregarServicios, ServiciosInter::class.java)
            if(editar != null){
                intent = Intent(this@AgregarServicios, EditarPaqueteActivity::class.java)
            }

            val serviciosFinal = serviciosAgregados
            if(servicios != null){
                serviciosFinal += agregarServiciosAdapter.serviciosSeleccionados
            }
            intent.putExtra("usuario", intermediario)
            intent.putExtra("servicios", ArrayList(serviciosFinal))
            intent.putExtra("nombrePaquete", nombrePaquete)
            intent.putExtra("fecha", fechaPaquete)
            intent.putExtra("costo", costoPaquete)
            intent.putExtra("dia", day)
            intent.putExtra("mes", month)
            intent.putExtra("year", year)
            if(id != 0){
                intent.putExtra("id", id)
            }
            if(urlImg != null){
                intent.putExtra("urlImagen", urlImg)
            }
            startActivity(intent)
        }
    }
}