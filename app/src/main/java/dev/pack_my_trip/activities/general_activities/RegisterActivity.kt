package dev.pack_my_trip.activities.general_activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import dev.pack_my_trip.ConectionBack.Interfaces.OnCrearUsuario
import dev.pack_my_trip.Presenter.General.CrearUsuarioPresenter
import dev.pack_my_trip.Presenter.General.OnSubirImagen
import dev.pack_my_trip.R
import dev.pack_my_trip.databinding.ActivitySinginBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario
import java.io.ByteArrayOutputStream
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
    private lateinit var binding : ActivitySinginBinding
    private var registroUsuarioPresenter : CrearUsuarioPresenter = CrearUsuarioPresenter()
    private var firebaseStorage : FirebaseStorage? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editTextProfileName = findViewById(R.id.editTextProfileName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextDay = findViewById(R.id.editTextDay)
        editTextMonth = findViewById(R.id.editTextMonth)
        editTextYear = findViewById(R.id.editTextYear)
        spinnerRole = findViewById(R.id.spinnerRole)
        buttonRegister = findViewById(R.id.buttonRegister)
        firebaseStorage = FirebaseStorage.getInstance()

        manageButtons()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageButtons() {
        buttonRegister.setOnClickListener {
            registerUser()
        }

        eventoSubirImagenCamara()
        eventoSubirImagenGaleria()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerUser() {
        if (validateFields()) {
            val name = editTextProfileName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val day = editTextDay.text.toString().toInt()
            val month = editTextMonth.text.toString().toInt()
            val year = editTextYear.text.toString().toInt()
            val role = spinnerRole.selectedItem.toString()
            val dateBorn = "$day/$month/$year"
            val rolCaracter = role[0]
            val listaPaquetes = mutableListOf<PaqueteTuristico>()
            val user = Usuario(name, email, password, dateBorn, 0.0F, 0.0F, "Norte", rolCaracter, " ", listaPaquetes)

            // Imagen:
            subirImagen(user, object : OnSubirImagen {
                override fun onSubirImagen(url: String?) {
                    if (url != null) {
                        user.fotoPerfil = url
                    }
                }
            })

            // Subir usuario a la BD
            subirUsuario(user)
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("usuario", user)
            startActivity(intent)
            finish()

        }
        else{
            // Mostrar mensaje de error
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateFields() : Boolean{
        var isValid = true
        if (editTextProfileName.text.isEmpty()) {
            editTextProfileName.error = "El nombre es requerido"
            isValid = false
        }
        if (editTextEmail.text.isEmpty()) {
            editTextEmail.error = "El correo es requerido"
            isValid = false
        }
        if (editTextPassword.text.isEmpty()) {
            editTextPassword.error = "La contraseña es requerida"
            isValid = false
        }
        if (editTextDay.text.isEmpty()) {
            editTextDay.error = "El día es requerido"
            isValid = false
        }
        if (editTextMonth.text.isEmpty()) {
            editTextMonth.error = "El mes es requerido"
            isValid = false
        }
        if (editTextYear.text.isEmpty()) {
            editTextYear.error = "El año es requerido"
            isValid = false
        }
        return isValid
    }

    private fun subirImagen(usuario : Usuario, onSubirImagen: OnSubirImagen) {
        val bitmapPortada = (binding.imageViewTourist.drawable as BitmapDrawable).bitmap
        val carpetaDestino = firebaseStorage!!.reference.child(usuario.correo)
        val referenciaImagen = carpetaDestino.child(usuario.correo + "fotoPerfil"+ ".jpg")
        // Comprimir el bitmap a un stream de bytes
        val outputStream = ByteArrayOutputStream()
        bitmapPortada.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val bytes: ByteArray = outputStream.toByteArray()
        // Subir la imagen a Firebase Storage
        referenciaImagen.putBytes(bytes)
            .addOnSuccessListener { taskSnapshot ->
                // La imagen se subió exitosamente
                // Puedes obtener la URL de descarga de la imagen
                referenciaImagen.downloadUrl.addOnSuccessListener { uri ->
                    val url = uri.toString()
                    onSubirImagen.onSubirImagen(url)
                    usuario.fotoPerfil = url
                }
            }
            .addOnFailureListener { exception ->
                onSubirImagen.onSubirImagen(null)
            }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun subirUsuario (usuario : Usuario){
        registroUsuarioPresenter.registrarUsuario(usuario, this, object : OnCrearUsuario {
            override fun onCrearUsuario(realizado : Boolean) {
                if(realizado){
                   startActivity(Intent(this@RegisterActivity, LoginActivity::class.java).putExtra("usuario", usuario))
                }
            }
        })
    }

    private fun abrirSelectorImagen(requestCode: Int) {
        val intent = when (requestCode) {
            REQUEST_CAMERA -> Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            REQUEST_GALLERY -> Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            else -> throw IllegalArgumentException("Código de solicitud no válido: $requestCode")
        }
        startActivityForResult(intent, requestCode)
    }

    private fun cargarImagenEnImageView(data: Intent?) {
        data?.let {
            val bitmap = when {
                it.data != null -> {
                    val selectedImage = it.data
                    val inputStream = contentResolver.openInputStream(selectedImage!!)
                    BitmapFactory.decodeStream(inputStream)
                }
                it.extras?.containsKey("data") == true -> {
                    it.extras?.get("data") as Bitmap
                }
                else -> null
            }
            bitmap?.let { binding.imageViewTourist.setImageBitmap(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA, REQUEST_GALLERY -> cargarImagenEnImageView(data)
            }
        }
    }

    private fun eventoSubirImagenCamara() {
        binding.cameraButtonSigin.setOnClickListener {
            abrirSelectorImagen(REQUEST_CAMERA)
        }
    }

    private fun eventoSubirImagenGaleria() {
        binding.galleryButtonSigin.setOnClickListener {
            abrirSelectorImagen(REQUEST_GALLERY)
        }
    }

    companion object {
        private const val REQUEST_CAMERA = 1
        private const val REQUEST_GALLERY = 2
    }
}
