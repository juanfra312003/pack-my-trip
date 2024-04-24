package dev.pack_my_trip.activities.operator_activities

import android.annotation.SuppressLint
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
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import dev.pack_my_trip.ConectionBack.Interfaces.OnEditarServicio
import dev.pack_my_trip.Presenter.Operador.EditarServicioPresenter
import dev.pack_my_trip.Presenter.Operador.OnSubirImagen
import dev.pack_my_trip.R
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime

class EditarServicio : AppCompatActivity() {
    private lateinit var aceptarCambiosBtn: Button
    private lateinit var caracteristicasETxt: EditText
    private lateinit var nombreServicioETxt : EditText
    private lateinit var seleccionarHora: Button
    private lateinit var seleccionarFecha: Button
    private lateinit var editarServicioPresenter: EditarServicioPresenter
    private lateinit var fechaHoraTxt: TextView
    private lateinit var servicio: Servicio
    private lateinit var precioEditText: EditText
    private lateinit var limiteDiarioEditText: EditText
    private lateinit var portadaImgView: ImageView
    private lateinit var subirImagenBtn: Button
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    private var firebaseStorage: FirebaseStorage? = null
    private lateinit var usuario: Usuario
    private var cambioFecha: Boolean = false
    private var cambioHora: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_servicio)
        servicio = intent.getSerializableExtra("servicio") as Servicio
        usuario = intent.getSerializableExtra("usuario") as Usuario
        inicializarVariables()
        editarServicioPresenter = EditarServicioPresenter(fechaHoraTxt, this);
        firebaseStorage = FirebaseStorage.getInstance()
        var fechaHora = LocalDateTime.parse(servicio.fechaHora)
        this.year = fechaHora.year
        this.month = fechaHora.monthValue
        this.day = fechaHora.dayOfMonth
        this.hour = fechaHora.hour
        this.minute = fechaHora.minute
        eventoRegistarServicio()
        eventoSeleccionarFecha()
        eventoSeleccionarHora()
        eventoSubirImagen()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun inicializarVariables(){
        aceptarCambiosBtn = findViewById(R.id.aceptarCambiosBtn)
        caracteristicasETxt = findViewById(R.id.caracteristicasEdtETxt)
        caracteristicasETxt.setSelection(0)
        caracteristicasETxt.gravity = Gravity.START or Gravity.TOP
        caracteristicasETxt.setText(servicio.caracteristicas)
        nombreServicioETxt = findViewById(R.id.nombreServicioEdtETxt)
        nombreServicioETxt.isEnabled = false
        nombreServicioETxt.setText(servicio.nombre)
        seleccionarHora = findViewById(R.id.seleccionarHoraEdtBtn)
        seleccionarFecha = findViewById(R.id.seleccionarFechaEdtBtn)
        fechaHoraTxt = findViewById(R.id.fechaHoraEdtTxt)
        val fechaHora = LocalDateTime.parse(servicio.fechaHora)
        fechaHoraTxt.text = "El servicio se efectuará el "+ fechaHora.dayOfMonth + "/" + fechaHora.monthValue  + "/" + fechaHora.year + " a las "+
                fechaHora.hour + ":" + fechaHora.minute
        fechaHoraTxt.visibility = View.VISIBLE
        precioEditText = findViewById(R.id.precioServicioEdtETxt)
        precioEditText.setText(servicio.precio.toString())
        limiteDiarioEditText = findViewById(R.id.limiteServicioEdtETxt)
        limiteDiarioEditText.setText(servicio.limiteDiario.toString())
        portadaImgView = findViewById(R.id.portadaEdtImgView)
        subirImagenBtn = findViewById(R.id.subirImagenEdtBtn)
        if(!servicio.portada.isEmpty()){
            Picasso.get().load(servicio.portada).placeholder(R.drawable.no_disponible).error(R.drawable.no_disponible).into(portadaImgView)
        }
        else{
            portadaImgView.setImageResource(R.drawable.no_disponible)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eventoRegistarServicio(){
        aceptarCambiosBtn.setOnClickListener{
            if(cambioFecha){
                this.day = editarServicioPresenter.day
                this.month = editarServicioPresenter.month
                this.year = editarServicioPresenter.year
            }
            if(cambioHora){
                this.hour = editarServicioPresenter.hour
                this.minute = editarServicioPresenter.minute
            }
            if(precioEditText.text.equals("") || limiteDiarioEditText.text.equals("") ||
                caracteristicasETxt.text.equals("")){
                Toast.makeText(this@EditarServicio, "Complete todos los datos antes de registrar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }
            subirImagen(nombreServicioETxt.text.toString(), LocalDateTime.of(year, month, day, hour, minute), object:
                OnSubirImagen {
                override fun onSubirImagen(url: String?) {
                    if(url == null){
                        Toast.makeText(this@EditarServicio, "no se pudo subir la imagen", Toast.LENGTH_SHORT).show()
                        return;
                    }
                    val servicio = Servicio(this@EditarServicio.servicio.id, nombreServicioETxt.text.toString(), precioEditText.text.toString().toFloat(), limiteDiarioEditText.text.toString().toInt(),
                        caracteristicasETxt.text.toString(), url, LocalDateTime.of(year, month, day, hour, minute).toString(),usuario.correo)
                    subirServicio(servicio)
                }
            })
        }
    }

    fun subirImagen(nombreServicio: String, fechaServicio: LocalDateTime, onSubirImagen: OnSubirImagen) {
        val bitmapPortada = (portadaImgView.drawable as BitmapDrawable).bitmap
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
        editarServicioPresenter.editarServicio(servicio, this@EditarServicio, object:
            OnEditarServicio {
            override fun onEditarServicio(realizado: Boolean) {
                if(realizado){
                    val intent = Intent(this@EditarServicio, DashboardOperator::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
                    startActivity(intent) //Inicia la actividad
                    Toast.makeText(this@EditarServicio, "Edición realizada con éxito", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@EditarServicio, "Hubo un error durante la edición", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun eventoSeleccionarFecha(){
        seleccionarFecha.setOnClickListener{
            editarServicioPresenter.pedirFecha()
            this.fechaHoraTxt = editarServicioPresenter.fechaHoraTxt
            fechaHoraTxt.visibility = View.VISIBLE
            cambioFecha = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eventoSeleccionarHora(){
        seleccionarHora.setOnClickListener{
            val fechaHora = LocalDateTime.parse(servicio.fechaHora)
            editarServicioPresenter.pedirHora(fechaHora.year,fechaHora.monthValue, fechaHora.dayOfMonth)
            this.fechaHoraTxt = editarServicioPresenter.fechaHoraTxt
            fechaHoraTxt.visibility = View.VISIBLE
            cambioHora = true
        }
    }

    fun eventoSubirImagen(){
        subirImagenBtn.setOnClickListener {
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
            portadaImgView.setImageBitmap(bitmap)
        }
    }

}