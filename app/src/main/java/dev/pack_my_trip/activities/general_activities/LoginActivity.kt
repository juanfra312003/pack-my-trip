package dev.pack_my_trip.activities.general_activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.R
import android.util.Log
import dev.pack_my_trip.activities.tourist_activities.DashboardTouristActivity
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
            Toast.makeText(this, "Pasando al login.", Toast.LENGTH_SHORT).show()

            performLogin()
        }

        buttonRegister.setOnClickListener {
            try {
                startActivity(Intent(this, RegisterActivity::class.java))
            } catch (e: Exception) {
                val errorMessage = "Error al abrir el registro: ${e.message}"
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", errorMessage, e)
            }
        }

    }

    private fun performLogin() {
        startActivity(Intent(this, DashboardTouristActivity::class.java))
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