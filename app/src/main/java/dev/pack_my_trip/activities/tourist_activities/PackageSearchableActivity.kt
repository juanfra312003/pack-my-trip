package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.tourist_adapters.ServicesPackageAdapter
import dev.pack_my_trip.databinding.ActivityPackageSearchableBinding
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.PaqueteTuristico
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista
import dev.pack_my_trip.models.models_tourist.Turista
import java.util.Date

class PackageSearchableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPackageSearchableBinding
    private lateinit var turista: Usuario
    private lateinit var paquete: PaqueteTuristico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageSearchableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        turista = intent.getSerializableExtra("usuario") as Usuario
        paquete = intent.getSerializableExtra("paquete_nuevo") as PaqueteTuristico

        load_values()
        manageButtons()
    }

    // Funcion para cargar los valores
    private fun load_values() {
        // Cargar valores
        binding.organizadorTextEditablePackageTSearch.text = paquete.nombreOrganizador
        binding.textFieldPackageNameEditableSearch.text = paquete.nombre
        binding.costoEditableTextPackagetouristSearch.text = "$" + paquete.precio.toString()

        // Pintar la imagen
        when (paquete.tipo) {
            //TODO: Cambiar las imagenes por las que se encuentran en el proyecto en firebase storage
            "Volcan" -> binding.imageViewPackageTypeSearch.setImageResource(R.drawable.volcan)
            "Buceo" -> binding.imageViewPackageTypeSearch.setImageResource(R.drawable.buceo)
            "Aviario" -> binding.imageViewPackageTypeSearch.setImageResource(R.drawable.aviario)
        }

        // Mostrar los servicios a través del adapter del mismo
        //binding.listViewServicesSearchPackage.adapter = ServicesPackageAdapter(this, paquete.servicios.toMutableList())
    }

    private fun manageButtons() {
        binding.buttonProgramPackage.setOnClickListener {
            // Tomar los valores de año, mes y día del calendario
            val fechaLong = binding.calendarViewPackage.date

            // Pasar a fecha el long del calendaryview
            val fecha = Date(fechaLong)

            // TODO: Guardar el turista en la base de datos con el nuevo paquete y colocar la actividad como start for result

            // Redirigir a la actividdad del dashboard del turista
            startActivity(Intent(this, DashboardTouristActivity::class.java))
        }

        binding.backButtonSearchPackage.setOnClickListener {
            val intent = Intent(this, SearchPackagesActivity::class.java)
            intent.putExtra("turista", turista)
            startActivity(intent)
        }
    }
}

