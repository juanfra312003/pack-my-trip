package dev.pack_my_trip.activities.inter_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.Operador.MisServiciosAdapter
import dev.pack_my_trip.databinding.ActivityAgregarServiciosBinding
import dev.pack_my_trip.databinding.ActivityPaqueteTuristicoCreadoBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario
import java.time.LocalDate

class PaqueteTuristicoCreado : AppCompatActivity() {
    private lateinit var binding : ActivityPaqueteTuristicoCreadoBinding
    private lateinit var paqueteTuristico: PaqueteTuristico
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaqueteTuristicoCreadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        paqueteTuristico = intent.getSerializableExtra("paquete") as PaqueteTuristico
        usuario = intent.getSerializableExtra("usuario") as Usuario
        inicializarComponentes()
    }

    fun inicializarComponentes(){
        binding.textFieldPackageName.text = "¡ Tu paquete turístico comienza el " + paqueteTuristico.fechaHora + "!"
        Picasso.get().load(paqueteTuristico.imagen).placeholder(R.drawable.no_disponible).error(R.drawable.no_disponible).into(binding.imageViewPackageType)
        binding.detalleLugar.text = paqueteTuristico.nombre
        binding.fechaInfo.text = paqueteTuristico.nombre
        binding.listViewServicios.adapter = MisServiciosAdapter(this, paqueteTuristico.listaServicios, usuario);
    }
}