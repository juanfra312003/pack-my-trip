package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.adapters.tourist_adapters.PackagesTouristAdapter
import dev.pack_my_trip.databinding.ActivityDashboardTouristBinding
import dev.pack_my_trip.models.models_tourist.PaqueteTuristico
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista
import dev.pack_my_trip.models.models_tourist.ServicioTuristico
import dev.pack_my_trip.models.models_tourist.Turista

class DashboardTouristActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardTouristBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MANEJO DE TURISTA Y PAQUETES "ADQUIRIDOS"
        var turista = manageTourist()
        var lista_paquetes = managePackages()
        var fecha = java.util.Date()
        for (paquete in lista_paquetes){
            var paquetes_turista = PaquetesPorTurista(turista.uuid, paquete, fecha)
            turista.paquetes.add(paquetes_turista)
        }

        // MOSTRAR LA LISTA DE PAQUETES
        binding.listTouristPackages.adapter = PackagesTouristAdapter(this, turista.paquetes)
        binding.listTouristPackages.setOnItemClickListener(){ parent, view, position, id ->
            var paquete_turista = turista.paquetes[position]

            var intent = Intent(baseContext, PackageTouristActivity::class.java)
            intent.putExtra("paquete_turista", paquete_turista)
            startActivity(intent)
        }
    }

    private fun manageTourist () : Turista {
        // Turista.
        var turista = Turista("Juan", "juanframireze@gmail.com")
        turista.latitud = 4.60971
        turista.longitud = -74.08175
        return turista;
    }

    private fun managePackages () : MutableList<PaqueteTuristico>{
        var paquetes = mutableListOf<PaqueteTuristico>()

        // Paquete Volcan arenal
        var paquete_volcan_arenal = PaqueteTuristico("Expedición Volcan Arenal", 150.45, "Hotel San Bosco", "Volcan")
        paquete_volcan_arenal.servicios.add(ServicioTuristico("Expedición Volcánica", 10.461352, -84.701013, "Expedición al volcán arenal de Costa Rica"))
        paquete_volcan_arenal.servicios.add(ServicioTuristico("Caminata por el volcán", 10.4614, -84.702, "Caminata por el volcán arenal de Costa Rica"))
        paquete_volcan_arenal.servicios.add(ServicioTuristico("Transporte ida y vuelta", 10.47089 , -84.64535, "Transporte desde la fortuna"))
        paquete_volcan_arenal.precio = 150.45

        // Añadir los paquetes.
        paquetes.add(paquete_volcan_arenal)
        return paquetes;
    }
}