package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.adapters.tourist_adapters.PackagesTouristAdapter
import dev.pack_my_trip.databinding.ActivityDashboardTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
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
        val turista = manageTourist()
        val listaPaquetes = managePackages()
        val fecha = java.util.Date()
        for (paquete in listaPaquetes){
            //val paquetesPorTurista = PaquetesPorTurista(turista.uuid, paquete, fecha)
            //turista.paquetes.add(paquetesPorTurista)
        }

        // MOSTRAR LA LISTA DE PAQUETES
        binding.listTouristPackages.adapter = PackagesTouristAdapter(this, turista.paquetes)
        binding.listTouristPackages.setOnItemClickListener { _, _, position, _ ->
            val paqueteTurista = turista.paquetes[position]

            val intent = Intent(baseContext, PackageTouristActivity::class.java)
            intent.putExtra("paquete_turista", paqueteTurista)
            startActivity(intent)
        }

        // ACTUALIZAR EL NOMBRE DEL TURISTA.
        binding.textoBienvenido.text = "Bienvenido de nuevo, ${turista.nombre}"

        // MANEJO DE BOTÓN DE BÚSQUEDA DE PAQUETES CON EL TURISTA CÓMO PARÁMETRO PARA LA ACTIVIDAD DE BÚSQUEDA DE PAQUETES
        manageButtons(turista)
    }

    private fun manageTourist () : Turista {
        // Turista.
        val turista = Turista("Juan", "juanframireze@gmail.com")
        turista.latitud = 4.60971
        turista.longitud = -74.08175
        return turista
    }

    private fun managePackages () : MutableList<PaqueteTuristico>{
        val paquetes = mutableListOf<PaqueteTuristico>()

        // Paquete Volcan arenal
        /*val paqueteVolcanArenal = PaqueteTuristico("Expedición Volcan Arenal", 150.45, "Hotel San Bosco", "Volcan")
        paqueteVolcanArenal.servicios.add(Servicio(0, "Expedición al volcán arenal de Costa Rica", 58f, 15, "aa", "operador"))
        paqueteVolcanArenal.servicios.add(Servicio("Caminata por el volcán", 10.4614, -84.702, "Caminata por el volcán arenal de Costa Rica"))
        paqueteVolcanArenal.servicios.add(Servicioo("Transporte ida y vuelta", 10.47089 , -84.64535, "Transporte desde la fortuna"))
        paqueteVolcanArenal.precio = 150.45

        // Añadir los paquetes.
        paquetes.add(paqueteVolcanArenal)*/
        return paquetes
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
