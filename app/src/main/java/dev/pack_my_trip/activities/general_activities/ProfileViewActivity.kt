package dev.pack_my_trip.activities.general_activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import dev.pack_my_trip.ConectionBack.Interfaces.OnEditarUsuario
import dev.pack_my_trip.Presenter.General.EditarUsuarioPresenter
import dev.pack_my_trip.Presenter.General.OnSubirImagen
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.inter_activities.DashboardInter
import dev.pack_my_trip.activities.operator_activities.DashboardOperator
import dev.pack_my_trip.activities.tourist_activities.DashboardTouristActivity
import dev.pack_my_trip.databinding.ActivityProfileViewBinding
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.Turista
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime

class ProfileViewActivity : AppCompatActivity() {

    private lateinit var usuario: Usuario
    private lateinit var binding : ActivityProfileViewBinding
    private var editarUsuarioPresenter = EditarUsuarioPresenter()
    private var firebaseStorage : FirebaseStorage? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        firebaseStorage = FirebaseStorage.getInstance()


        inicializarValores()
        manageButtons()
        eventoSubirImagenGaleria()
        eventoSubirImagenCamara()
    }

    private fun inicializarValores(){
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
            Picasso.get().load(urlImg).into(binding.imagenProfileUser)
        }
        /*
        else{
            binding.imagenProfileUser.setImageResource(R.drawable.usuario)
        }
         */
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageButtons ()
    {
        binding.button.setOnClickListener {
            val userName = binding.editableProfileUsername.text.toString()
            val password = binding.editablePasswordProfile.text.toString().trim()

            if (password.isNotEmpty()){
                usuario.contrasenha = password
            }

            if (userName.isNotEmpty()){
                usuario.usuario = userName
            }
            subirImagen(usuario.correo, object : OnSubirImagen{
                override fun onSubirImagen(url: String?) {
                    if(url != null){
                        usuario.fotoPerfil = url
                    }
                    subirUsuario(usuario)
                }
            })
            subirUsuario(usuario)
        }
    }

    private fun subirImagen(usuarioCorreo : String, onSubirImagen: OnSubirImagen) {
        val bitmapPortada = (binding.imagenProfileUser.drawable as BitmapDrawable).bitmap
        val carpetaDestino = firebaseStorage!!.reference.child(usuario.correo)
        val referenciaImagen = carpetaDestino.child(usuarioCorreo + "fotoPerfil"+ ".jpg")
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
    private fun subirUsuario(usuario : Usuario){
        editarUsuarioPresenter.editarUsuario(usuario, this, object : OnEditarUsuario{
            override fun onEditarUsuario(realizado : Boolean) {
                if(realizado){
                    when (usuario.tipo){
                        'T' -> {
                            val intent = Intent(baseContext, DashboardTouristActivity::class.java)
                            intent.putExtra("usuario", usuario)
                            startActivity(intent)
                            finish()
                        }
                        'O' -> {
                            val intent = Intent(baseContext, DashboardOperator::class.java)
                            intent.putExtra("usuario", usuario)
                            startActivity(intent)
                            finish()
                        }
                        'I' -> {
                            val intent = Intent(baseContext, DashboardInter::class.java)
                            intent.putExtra("usuario", usuario)
                            startActivity(intent)
                            finish()
                        }
                    }
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
            bitmap?.let { binding.imagenProfileUser.setImageBitmap(it) }
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
        binding.buttonUploadCameraProfle.setOnClickListener {
            abrirSelectorImagen(REQUEST_CAMERA)
        }
    }

    private fun eventoSubirImagenGaleria() {
        binding.bottonUploadGalleryProfile.setOnClickListener {
            abrirSelectorImagen(REQUEST_GALLERY)
        }
    }

    companion object {
        private const val REQUEST_CAMERA = 1
        private const val REQUEST_GALLERY = 2
    }
}