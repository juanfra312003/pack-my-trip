package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import dev.pack_my_trip.ConectionBack.OnCrearServicio
import dev.pack_my_trip.Presenter.RegistroServiciosPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime

class RegistroServicios : AppCompatActivity() {
    private lateinit var registrarServicioBtn: Button
    private lateinit var caracteristicasETxt: EditText
    private lateinit var subirPortada: Button
    private lateinit var seleccionarHora: Button
    private lateinit var seleccionarFecha: Button
    private lateinit var portada: ImageView
    private lateinit var registroServiciosPresenter: RegistroServiciosPresenter
    private lateinit var fechaHoraTxt: TextView
    private lateinit var nombreServicioEditText: EditText
    private lateinit var precioServicioEditText: EditText
    private lateinit var limiteServicioEditText: EditText
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    private lateinit var usuario: Usuario
    private var fechaPuesta: Boolean = false
    private var horaPuesta: Boolean = false
    private var firebaseStorage: FirebaseStorage? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_registro_servicios)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        inicializarVariables()
        registroServiciosPresenter = RegistroServiciosPresenter(fechaHoraTxt, this);
        firebaseStorage = FirebaseStorage.getInstance()
        eventoSubirImagen()
        eventoSeleccionarFecha()
        eventoSeleccionarHora()
        eventoRegistrarServicio()
    }

    fun inicializarVariables(){
        registrarServicioBtn = findViewById(R.id.registrarServicioRegBtn)
        nombreServicioEditText = findViewById(R.id.nombreServicioETxt)
        precioServicioEditText = findViewById(R.id.precioServicioETxt)
        limiteServicioEditText = findViewById(R.id.limiteVisitaServicioETxt)
        caracteristicasETxt = findViewById(R.id.caracteristicasRegETxt)
        caracteristicasETxt.setSelection(0)
        caracteristicasETxt.gravity = Gravity.START or Gravity.TOP
        subirPortada = findViewById(R.id.subirPortadaButton)
        seleccionarHora = findViewById(R.id.seleccionarHoraBtn)
        seleccionarFecha = findViewById(R.id.seleccionarFechaBtn)
        fechaHoraTxt = findViewById(R.id.fechaHoraRegTxt)
        portada = findViewById(R.id.portadaImgView)
    }

    fun eventoSubirImagen(){
        subirPortada.setOnClickListener {
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
        seleccionarFecha.setOnClickListener{
            registroServiciosPresenter.pedirFecha()
            fechaPuesta = true
            this.fechaHoraTxt = registroServiciosPresenter.fechaHoraTxt
            fechaHoraTxt.visibility = View.VISIBLE
        }
    }

    fun eventoSeleccionarHora(){
        seleccionarHora.setOnClickListener{
            registroServiciosPresenter.pedirHora()
            this.fechaHoraTxt = registroServiciosPresenter.fechaHoraTxt
            horaPuesta = true
            fechaHoraTxt.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eventoRegistrarServicio() {
        registrarServicioBtn.setOnClickListener {
            this.day = registroServiciosPresenter.day
            this.month = registroServiciosPresenter.month
            this.year = registroServiciosPresenter.year
            this.hour = registroServiciosPresenter.hour
            this.minute = registroServiciosPresenter.minute
            if(nombreServicioEditText.text.equals("") || precioServicioEditText.text.equals("") || limiteServicioEditText.text.equals("") ||
                caracteristicasETxt.text.equals("") || !fechaPuesta || !horaPuesta || portada.visibility == View.GONE){
                Toast.makeText(this@RegistroServicios, "Complete todos los datos antes de registrar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }
            subirImagen(nombreServicioEditText.text.toString(), LocalDateTime.of(year, month, day, hour, minute), object: OnSubirImagen{
                override fun onSubirImagen(url: String?) {
                    if(url == null){
                        Toast.makeText(this@RegistroServicios, "no se pudo subir la imagen", Toast.LENGTH_SHORT).show()
                        return;
                    }
                    val servicio = Servicio(0, nombreServicioEditText.text.toString(), precioServicioEditText.text.toString().toFloat(), limiteServicioEditText.text.toString().toInt(),
                        caracteristicasETxt.text.toString(), url, LocalDateTime.of(year, month, day, hour, minute).toString(),usuario.correo)
                    subirServicio(servicio)
                }
            })
        }
    }

    fun subirImagen(nombreServicio: String, fechaServicio: LocalDateTime, onSubirImagen: OnSubirImagen) {
        val bitmapPortada = (portada.drawable as BitmapDrawable).bitmap
        val carpetaDestino = firebaseStorage!!.reference.child(usuario.correo)
        val referenciaImagen = carpetaDestino.child(nombreServicio +fechaServicio.toString() + ".jpg")
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

    fun subirServicio(servicio: Servicio){
        registroServiciosPresenter.registrarServicio(servicio, this@RegistroServicios, object: OnCrearServicio{
            override fun onCrearServicio(realizado: Boolean) {
                if(realizado){
                    val intent = Intent(this@RegistroServicios, DashboardOperator::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
                    startActivity(intent) //Inicia la actividad
                    Toast.makeText(this@RegistroServicios, "Registro realizado con éxito", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@RegistroServicios, "Hubo un error durante el registro", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}