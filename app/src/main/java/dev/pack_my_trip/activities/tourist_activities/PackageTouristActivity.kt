package dev.pack_my_trip.activities.tourist_activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.general_activities.ChatActivity
import dev.pack_my_trip.adapters.tourist_adapters.ServicesPackageAdapter
import dev.pack_my_trip.databinding.ActivityPackageTouristBinding
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.PaqueteTuristico
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista

class PackageTouristActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPackageTouristBinding
    private lateinit var paqueteTurista : dev.pack_my_trip.models.data_model.PaqueteTuristico
    private lateinit var usuario : Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)
        paqueteTurista = intent.getSerializableExtra("paquete_turista") as dev.pack_my_trip.models.data_model.PaqueteTuristico
        usuario = intent.getSerializableExtra("usuario") as Usuario

        cargarValores()
        manageNavBar()
        manageButtons()
    }

    @SuppressLint("SetTextI18n")
    private fun cargarValores(){
        binding.costoEditableTextPackagetourist.text = "$" + paqueteTurista.precioDolares.toString()
        val fechaPaquete = paqueteTurista.fechaHora
        binding.fechaEditableTextPackagetourist.text = fechaPaquete
        binding.organizadorTextEditablePackageT.text = paqueteTurista.correoIntermediario
        binding.textFieldPackageNameEditable.text = paqueteTurista.nombre
        if (paqueteTurista.imagen != null && paqueteTurista.imagen != ""){
            Picasso.get().load(paqueteTurista.imagen).placeholder(R.drawable.no_disponible).error(R.drawable.no_disponible).into(binding.imageViewPackageType)
        } else {
            binding.imageViewPackageType.setImageResource(R.drawable.paquete_general)
        }
        //Mostrar servicios del paquete.
        binding.listViewServicesPackage.adapter = ServicesPackageAdapter(this, paqueteTurista.listaServicios.toMutableList())
    }

    private fun manageNavBar(){
        binding.bottomNavigationViewTourist.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuBack -> {
                    startActivity(Intent(this, DashboardTouristActivity::class.java))
                    true
                }
                R.id.menuChat -> {
                    val intent = Intent(this, ChatTouristActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                    true
                }
                R.id.menuMap -> {
                    val intent = Intent(this, TouristMapActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun manageButtons (){
        binding.buttonInfoAditionalPackage.setOnClickListener {
            val intent = Intent(this, DetailsPackageTourist::class.java)
            intent.putExtra("paquete_turista", paqueteTurista)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }
}