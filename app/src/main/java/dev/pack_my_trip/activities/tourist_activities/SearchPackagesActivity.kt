package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivitySearchPackagesBinding
import dev.pack_my_trip.models.models_tourist.PaqueteTuristico
import dev.pack_my_trip.models.models_tourist.ServicioTuristico
import dev.pack_my_trip.models.models_tourist.Turista

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

        // Mostrar los paquetes turísticos.
        var intent = Intent(this, PackageTouristActivity::class.java)
        intent.putExtra("turista", turista)
    }

    private fun managePackages () : MutableList<PaqueteTuristico>{
        val paquetes = mutableListOf<PaqueteTuristico>()

        // Paquete Volcan arenal
        val paqueteVolcanArenal = PaqueteTuristico("Expedición Volcan Arenal", 150.45, "Hotel San Bosco", "Volcan")
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
        return paquetes
    }
}