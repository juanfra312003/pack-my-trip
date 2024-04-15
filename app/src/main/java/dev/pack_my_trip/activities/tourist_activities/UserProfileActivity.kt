package dev.pack_my_trip.activities.tourist_activities

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.R
import dev.pack_my_trip.models.models_tourist.Turista

class UserProfileActivity : AppCompatActivity() {

    private lateinit var textViewUserName: TextView
    private lateinit var textViewUserEmail: TextView
    private lateinit var imageViewProfile: ImageView
    private lateinit var imageViewSettings: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inicializa las vistas
        textViewUserName = findViewById(R.id.textViewUserName)
        textViewUserEmail = findViewById(R.id.textViewUserEmail)
        imageViewProfile = findViewById(R.id.imageViewProfile)
        imageViewSettings = findViewById(R.id.imageViewSettings)

        cargarPerfilUsuario()
    }

    private fun cargarPerfilUsuario() {
        val usuarioLogueado = obtenerUsuarioLogueado(this)
        if (usuarioLogueado != null) {
            textViewUserName.text = usuarioLogueado.nombre
            textViewUserEmail.text = usuarioLogueado.correo

        } else {
            //TODO: Agregar logica de error
        }
    }

    private fun obtenerUsuarioLogueado(context: Context): Turista? {
        val nombre = "Usuario Ejemplo"
        val correo = "usuario@example.com"
        return Turista(nombre, correo, "", 1, 1, 1990, "Rol no especificado")
    }
}
