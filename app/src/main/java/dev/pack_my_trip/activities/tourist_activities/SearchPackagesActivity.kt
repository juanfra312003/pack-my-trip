package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetAllPaquetes
import dev.pack_my_trip.Presenter.Turista.GetAllPaquetesPresenter
import dev.pack_my_trip.activities.general_activities.RegionActivity
import dev.pack_my_trip.adapters.tourist_adapters.PackagesSearchAdapter
import dev.pack_my_trip.databinding.ActivitySearchPackagesBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario
import java.util.Locale

class SearchPackagesActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchPackagesBinding
    private lateinit var usuario : Usuario
    private var getAllPaquetesPresenter : GetAllPaquetesPresenter = GetAllPaquetesPresenter()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPackagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario

        loadValues()
        manageButtons()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadValues(){
        getAllPaquetesPresenter.getAllPaquetes(this, object : OnGetAllPaquetes {
            override fun onGetAllPaquetes(paquetes: List<PaqueteTuristico>) {
                binding.listViewPackagesSearchTourist.adapter = PackagesSearchAdapter(this@SearchPackagesActivity, paquetes.toMutableList())
                manageFilterButton(paquetes.toMutableList())
                binding.listViewPackagesSearchTourist.setOnItemClickListener { _, _, position, _ ->
                    val paqueteNuevo = paquetes[position]
                    val intent = Intent(baseContext, PackageSearchableActivity::class.java)
                    intent.putExtra("paquete_nuevo", paqueteNuevo)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                }
            }
        })
    }

    private fun manageButtons (){
        binding.backButtonPackagesDetails.setOnClickListener {
            val intent = Intent(this, DashboardTouristActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        binding.buttonChangeRegionSearchPackages.setOnClickListener {
               val intent = Intent(this, RegionActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageFilterButton (paquetes : MutableList<PaqueteTuristico>){
        binding.buttonSearchFiltrePackages.setOnClickListener {
            val text = binding.editableTextSearch.text.toString()
            if (text.isEmpty()) {
                binding.listViewPackagesSearchTourist.adapter = PackagesSearchAdapter(this, paquetes)
            }
            else{
                val paquetesFiltrados = paquetes.filter { it.nombre.lowercase(Locale.ROOT).contains(
                    text.lowercase(Locale.ROOT)
                ) }
                binding.listViewPackagesSearchTourist.adapter = PackagesSearchAdapter(this, paquetesFiltrados.toMutableList())
            }
        }
    }
}