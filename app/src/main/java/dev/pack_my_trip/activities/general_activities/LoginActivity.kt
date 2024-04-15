package dev.pack_my_trip.activities.general_activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.tourist_activities.TouristMapActivity
import dev.pack_my_trip.activities.tourist_activities.UploadDocumentActivity
import dev.pack_my_trip.models.models_tourist.Turista  // Asegúrate de que el modelo y la ruta sean correctos.

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializa las vistas
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            performLogin()
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin() {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val userRole = authenticateUser(username, password)

        when (userRole) {
            "Interlocutor" -> {
                startActivity(Intent(this, UploadDocumentActivity::class.java))
            }
            "Usuario" -> {
                startActivity(Intent(this, TouristMapActivity::class.java))
            }
            null -> Toast.makeText(this, "Credenciales incorrectas o usuario no encontrado.", Toast.LENGTH_LONG).show()
        }

        editTextPassword.text.clear()
    }

    private fun authenticateUser(username: String, password: String): String? {

        if (username == "interlocutor@example.com" && password == "secure123") {
            return "Interlocutor"
        } else if (username == "usuario@example.com" && password == "secure123") {
            return "Usuario"
        }
        return null
    }
}
