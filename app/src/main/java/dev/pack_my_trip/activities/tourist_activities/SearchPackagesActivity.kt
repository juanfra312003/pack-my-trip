package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.tourist_adapters.PackagesSearchAdapter
import dev.pack_my_trip.databinding.ActivitySearchPackagesBinding
import dev.pack_my_trip.models.models_tourist.PaqueteTuristico
import dev.pack_my_trip.models.models_tourist.ServicioTuristico
import dev.pack_my_trip.models.models_tourist.Turista
import java.util.Locale

class SearchPackagesActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchPackagesBinding
    private lateinit var turista : Turista


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPackagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el turista a partir de la actividad anterior.
        turista = intent.getSerializableExtra("turista") as Turista

        // Recibir los paquetes turísticos.
        // TODO: Recibir los paquetes turísticos de la base de datos.
        var paquetes = managePackages()

        // Manejo de botones
        manageButtons()

        // Mostrar los paquetes turísticos.
        binding.listViewPackagesSearchTourist.adapter = PackagesSearchAdapter(this, paquetes)

        binding.listViewPackagesSearchTourist.setOnItemClickListener { _, _, position, _ ->
            val paqueteNuevo = paquetes[position]
            val intent = Intent(baseContext, PackageSearchableActivity::class.java)
            intent.putExtra("paquete_nuevo", paqueteNuevo)
            intent.putExtra("turista", turista)
            startActivity(intent)
        }

        manageFilterButton(paquetes)
    }

    private fun managePackages () : MutableList<PaqueteTuristico>{
        val paquetes = mutableListOf<PaqueteTuristico>()

        // Paquete Volcan arenal
        /*val paqueteVolcanArenal = PaqueteTuristico("Expedición Volcan Arenal", 150.45, "Hotel San Bosco", "Volcan")
        paqueteVolcanArenal.servicios.add(ServicioTuristico("Expedición Volcánica", 10.461352, -84.701013, "Expedición al volcán arenal de Costa Rica"))
        paqueteVolcanArenal.servicios.add(ServicioTuristico("Caminata por el volcán", 10.4614, -84.702, "Caminata por el volcán arenal de Costa Rica"))
        paqueteVolcanArenal.servicios.add(ServicioTuristico("Transporte ida y vuelta", 10.47089 , -84.64535, "Transporte desde la fortuna"))
        paqueteVolcanArenal.precio = 150.45


        // Paquete buceo
        val paqueteBuceo = PaqueteTuristico("Buceo en Playa Ocotal ", 200.0, "Ocotal Reasort", "Buceo")
        paqueteBuceo.servicios.add(ServicioTuristico("Buceo", 10.4167, -85.8333, "Buceo en Playa Ocotal"))
        paqueteBuceo.servicios.add(ServicioTuristico("Almuerzo", 10.4167, -85.8333, "Almuerzo típio costarricense"))
        paqueteBuceo.servicios.add(ServicioTuristico("Transporte ida y vuelta", 10.4167, -85.7216, "Transporte desde el hotel"))

        // Paquete aviario
        val paqueteAviario = PaqueteTuristico("Visita al aviario", 50.0, "Aviario Nacional", "Aviario")
        paqueteAviario.servicios.add(ServicioTuristico("Visita al aviario", 10.4167, -85.7216, "Visita al aviario nacional"))
        paqueteAviario.servicios.add(ServicioTuristico("Almuerzo", 10.4167, -85.7216, "Almuerzo típico costarricense"))
        paqueteAviario.servicios.add(ServicioTuristico("Transporte ida y vuelta", 10.4251, -85.7319, "Transporte desde el hotel"))

        // Añadir los paquetes.
        paquetes.add(paqueteVolcanArenal)
        paquetes.add(paqueteBuceo)
        paquetes.add(paqueteAviario)*/
        return paquetes
    }

    private fun manageButtons (){
        binding.backButtonPackagesDetails.setOnClickListener {
            val intent = Intent(this, DashboardTouristActivity::class.java)
            startActivity(intent)
        }

        binding.buttonChangeRegionSearchPackages.setOnClickListener {
           // TODO: Implementar la funcionalidad de cambiar la región (Transición).
        }
    }

    private fun manageFilterButton (paquetes : MutableList<PaqueteTuristico>){
        binding.buttonSearchFiltrePackages.setOnClickListener {
            val text = binding.editableTextSearch.text.toString()

            if (text.isEmpty()) {
                // Mostrar todos los paquetes turísticos.
                binding.listViewPackagesSearchTourist.adapter = PackagesSearchAdapter(this, paquetes)
            }
            else{
                // Filtrar los paquetes turísticos con las letras en lower case.
                val paquetesFiltrados = paquetes.filter { it.nombre.lowercase(Locale.ROOT).contains(
                    text.lowercase(Locale.ROOT)
                ) }
                binding.listViewPackagesSearchTourist.adapter = PackagesSearchAdapter(this, paquetesFiltrados.toMutableList())
            }
        }
    }
}