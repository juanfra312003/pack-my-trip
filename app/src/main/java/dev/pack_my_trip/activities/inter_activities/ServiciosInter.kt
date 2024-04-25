package dev.pack_my_trip.activities.inter_activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.storage.FirebaseStorage
import dev.pack_my_trip.ConectionBack.Interfaces.OnCrearPaquete
import dev.pack_my_trip.ConectionBack.Interfaces.OnCrearServicio
import dev.pack_my_trip.Presenter.Intermediario.ServiciosInterPresenter
import dev.pack_my_trip.Presenter.Operador.OnSubirImagen
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.operator_activities.DashboardOperator
import dev.pack_my_trip.databinding.ActivityPaqueteTuristicoCreadoBinding
import dev.pack_my_trip.databinding.ActivityServiciosInterBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import kotlin.random.Random

class ServiciosInter : AppCompatActivity() {
    private lateinit var binding : ActivityServiciosInterBinding
    private lateinit var portada: ImageView
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var usuario: Usuario
    private lateinit var servicios: List<Servicio>
    private lateinit var serviciosInterPresenter: ServiciosInterPresenter
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var fechaPuesta: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiciosInterBinding.inflate(layoutInflater)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        setContentView(binding.root)
        inicializarVariables()
        onSubirImagen()
        eventoCrearPaquete()
        eventoSeleccionarFecha()
        eventoAgregarServicio()
    }

    fun inicializarVariables(){

    }

    fun onSubirImagen(){
        binding.subirImagenBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            val inputStream = contentResolver.openInputStream(selectedImage!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            portada.setImageBitmap(bitmap)
            portada.visibility = View.VISIBLE
        }
    }

    fun eventoSeleccionarFecha(){
        binding.selecccionarFecha.setOnClickListener{
            serviciosInterPresenter.pedirFecha()
            fechaPuesta = true
        }
    }

    fun eventoAgregarServicio(){
        binding.agregarServicioBtn.setOnClickListener{
            val intent = Intent(this, AgregarServicios::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eventoCrearPaquete(){
        binding.crearPaqueteBtn.setOnClickListener{
            this.day = serviciosInterPresenter.day
            this.month = serviciosInterPresenter.month
            this.year = serviciosInterPresenter.year
            if(binding.nombrePaqueteEtxt.text.equals("") || binding.organizadorTextEditablePackageT.text.equals("") || binding.fechaEditableTextPackagetourist.visibility == View.GONE ||
                binding.costoEditableTextPackagetourist.text.equals("") || !fechaPuesta || portada.visibility == View.GONE){
                Toast.makeText(this@ServiciosInter, "Complete todos los datos antes de registrar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }
            val random = Random(12345L)
            val numeroAleatorio = random.nextInt(24)
            val numeroAleatorio2 = random.nextInt(60)
            subirImagen(binding.nombrePaqueteEtxt.toString(), LocalDateTime.of(year, month, day, numeroAleatorio, numeroAleatorio2), object:
                OnSubirImagen {
                override fun onSubirImagen(url: String?) {
                    if(url == null){
                        Toast.makeText(this@ServiciosInter, "no se pudo subir la imagen", Toast.LENGTH_SHORT).show()
                        return
                    }

                    val paquete = PaqueteTuristico(0, binding.nombrePaqueteEtxt.text.toString(), LocalDateTime.of(year, month, day, 0, 0).toString(),
                        binding.costoEditableTextPackagetourist.text.toString().toFloat(), url, usuario.correo, servicios)
                    subirPaquete(paquete)
                }
            })
            val intent = Intent(this, DashboardInter::class.java)
            startActivity(intent)
        }
    }

    fun subirPaquete(paquete: PaqueteTuristico){
        serviciosInterPresenter.crearPaquete(paquete, this@ServiciosInter, object:
            OnCrearPaquete {
            override fun onCrearPaquete(creado: Boolean) {
                if(creado){
                    Toast.makeText(this@ServiciosInter, "se ha creado el paquete!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ServiciosInter, DashboardInter::class.java)
                    intent.putExtra("usuario", usuario)
                    return
                }
                Toast.makeText(this@ServiciosInter, "No se pudo crear el paquete", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun subirImagen(nombrePaquete: String, fechaPaquete: LocalDateTime, onSubirImagen: OnSubirImagen) {
        val bitmapPortada = (portada.drawable as BitmapDrawable).bitmap
        val carpetaDestino = firebaseStorage.reference.child(usuario.correo)
        val referenciaImagen = carpetaDestino.child(nombrePaquete +fechaPaquete.toString() + ".jpg")
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
                }
            }
            .addOnFailureListener { exception ->
                onSubirImagen.onSubirImagen(null)
            }
    }
}