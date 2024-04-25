package dev.pack_my_trip.activities.general_activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.squareup.picasso.Picasso
import dev.pack_my_trip.ConectionBack.Interfaces.OnEditarUsuario
import dev.pack_my_trip.Presenter.General.EditarUsuarioPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.inter_activities.DashboardInter
import dev.pack_my_trip.activities.operator_activities.DashboardOperator
import dev.pack_my_trip.activities.tourist_activities.DashboardTouristActivity
import dev.pack_my_trip.databinding.ActivityProfileViewBinding
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.Turista

class ProfileViewActivity : AppCompatActivity() {

    private lateinit var usuario: Usuario
    private lateinit var binding : ActivityProfileViewBinding
    private var editarUsuarioPresenter = EditarUsuarioPresenter()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el turista a partir de la actividad anterior.
        usuario = intent.getSerializableExtra("usuario") as Usuario

        // Cargar valores previos
        loadValues()

        // Manejo de botones
        manageButtons()
    }

    private fun loadValues(){
        // Valores de texto
        binding.editableProfileUsername.hint = usuario.usuario
        binding.textViewCorreo.text = usuario.correo
        when (usuario.tipo.toString()) {
            "T" -> binding.rolEditableProfile.text = "Turista"
            "O" -> binding.rolEditableProfile.text = "Operador"
            "I" -> binding.rolEditableProfile.text = "Intermediario"
        }

        // Cargar la imagen de perfil
        val urlImg : String? = usuario.fotoPerfil
        if(urlImg != null && !urlImg.isEmpty()){
            Picasso.get().load(urlImg).placeholder(R.drawable.no_disponible).error(R.drawable.no_disponible).into(binding.imagenProfileUser)
        }
        else{
            binding.imagenProfileUser.setImageResource(R.drawable.usuario_profile)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageButtons ()
    {
        binding.button.setOnClickListener {
            usuario.usuario = binding.editableProfileUsername.text.toString()
            val password = binding.editablePasswordProfile.text.toString().trim()
            if (password.isNotEmpty()){
                usuario.contrasenha = password
            }
            subirUsuario(usuario)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun subirUsuario(usuario : Usuario){
        editarUsuarioPresenter.editarUsuario(usuario, this, object : OnEditarUsuario{
            override fun onEditarUsuario(realizado : Boolean) {
                if(realizado){
                    when (usuario.tipo){
                        'T' -> {
                            val intent = Intent(this@ProfileViewActivity, DashboardTouristActivity::class.java)
                            intent.putExtra("usuario", usuario)
                            startActivity(intent)
                        }
                        'O' -> {
                            val intent = Intent(this@ProfileViewActivity, DashboardOperator::class.java)
                            intent.putExtra("usuario", usuario)
                            startActivity(intent)
                        }
                        'I' -> {
                            val intent = Intent(this@ProfileViewActivity, DashboardInter::class.java)
                            intent.putExtra("usuario", usuario)
                            startActivity(intent)
                        }
                    }
                }
            }
        })
    }
}