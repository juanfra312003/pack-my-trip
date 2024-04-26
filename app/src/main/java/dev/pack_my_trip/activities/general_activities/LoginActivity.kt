package dev.pack_my_trip.activities.general_activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.R
import android.util.Log
import androidx.annotation.RequiresApi
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetUsuario
import dev.pack_my_trip.Presenter.General.LogInPresenter
import dev.pack_my_trip.activities.operator_activities.DashboardOperator
import dev.pack_my_trip.activities.inter_activities.DashboardInter
import dev.pack_my_trip.activities.tourist_activities.DashboardTouristActivity
import dev.pack_my_trip.activities.tourist_activities.TouristMapActivity
import dev.pack_my_trip.activities.tourist_activities.UploadDocumentActivity
import dev.pack_my_trip.databinding.ActivityLoginBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.Turista  // Asegúrate de que el modelo y la ruta sean correctos.

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private var loginPresenter : LogInPresenter = LogInPresenter()
    private var usuarioLogin: Usuario? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonLogin.setOnClickListener {
            performLogin()
        }
        binding.buttonRegister.setOnClickListener{
            performRegister()
        }
    }

    private fun performRegister(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun performLogin() {
        inicializarValores()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun inicializarValores(){
        loginPresenter.getUsuario(binding.editTextUsername.text.toString(), binding.editTextPassword.text.toString(), baseContext, object : OnGetUsuario {
            override fun onGetUsuario(usuario: Usuario) {
                usuarioLogin = usuario
                verificacionLogIn() // Llamar a verificacionLogIn() una vez que usuarioLogin se haya inicializado
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun verificacionLogIn(){
        if (usuarioLogin == null){
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
        else{
            startActivityForUserType()
        }

        // Limpiar los campos de texto
        binding.editTextPassword.text.clear()
        binding.editTextUsername.text.clear()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startActivityForUserType() {
        usuarioLogin?.let { usuario ->
            when (usuario.tipo) {
                'T' -> {
                    startActivity(Intent(this, DashboardTouristActivity::class.java).putExtra("usuario", usuario))
                    finish()
                }
                'O' -> {
                    startActivity(Intent(this, DashboardOperator::class.java).putExtra("usuario", usuario))
                    finish()
                }
                'I' -> {
                    startActivity(Intent(this, DashboardInter::class.java).putExtra("usuario", usuario))
                    finish()
                }
            }
        }
    }

}