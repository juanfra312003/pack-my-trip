package dev.pack_my_trip.activities.general_activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.R
import dev.pack_my_trip.models.models_tourist.Turista
import java.lang.NumberFormatException

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextProfileName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextDay: EditText
    private lateinit var editTextMonth: EditText
    private lateinit var editTextYear: EditText
    private lateinit var spinnerRole: Spinner
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singin)

        // Inicializar vistas
        editTextProfileName = findViewById(R.id.editTextProfileName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextDay = findViewById(R.id.editTextDay)
        editTextMonth = findViewById(R.id.editTextMonth)
        editTextYear = findViewById(R.id.editTextYear)
        spinnerRole = findViewById(R.id.spinnerRole)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Configurar el listener del botón de registro
        buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        // Obtener los valores de los EditText y Spinner
        val name = editTextProfileName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val day = editTextDay.text.toString().toIntOrNull()
        val month = editTextMonth.text.toString().toIntOrNull()
        val year = editTextYear.text.toString().toIntOrNull()
        val role = spinnerRole.selectedItem.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() ||
            day == null || month == null || year == null) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val newTurista = Turista(
            nombre = name,
            correo = email,
            contraseña = password,
            diaNacimiento = day,
            mesNacimiento = month,
            añoNacimiento = year,
            rol = role
        )


        Toast.makeText(this, "Usuario registrado con éxito.", Toast.LENGTH_LONG).show()

        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)

        finish()

    }
}
