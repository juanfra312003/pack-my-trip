package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import dev.pack_my_trip.Presenter.RegistroServiciosPresenter
import dev.pack_my_trip.R

class RegistroServicios : AppCompatActivity() {
    private lateinit var registrarServicioBtn: Button
    private lateinit var caracteristicasETxt: EditText
    private lateinit var subirPortada: Button
    private lateinit var seleccionarHora: Button
    private lateinit var seleccionarFecha: Button
    private lateinit var portada: ImageView
    private lateinit var registroServiciosPresenter: RegistroServiciosPresenter
    private lateinit var fechaHoraTxt: TextView
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_servicios)
        inicializarVariables()
        registroServiciosPresenter = RegistroServiciosPresenter(fechaHoraTxt, this);
        eventoRegistarServicio()
        eventoSubirImagen()
        eventoSeleccionarFecha()
        eventoSeleccionarHora()
    }

    fun inicializarVariables(){
        registrarServicioBtn = findViewById(R.id.registrarServicioRegBtn)
        caracteristicasETxt = findViewById(R.id.caracteristicasRegETxt)
        caracteristicasETxt.setSelection(0)
        caracteristicasETxt.gravity = Gravity.START or Gravity.TOP
        subirPortada = findViewById(R.id.subirPortadaButton)
        seleccionarHora = findViewById(R.id.seleccionarHoraBtn)
        seleccionarFecha = findViewById(R.id.seleccionarFechaBtn)
        fechaHoraTxt = findViewById(R.id.fechaHoraRegTxt)
        portada = findViewById(R.id.portadaImgView)
    }

    fun eventoRegistarServicio(){
        registrarServicioBtn.setOnClickListener{
            val intent = Intent(this, DashboardOperator::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            startActivity(intent) //Inicia la actividad
        }
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
            this.day = registroServiciosPresenter.day
            this.month = registroServiciosPresenter.month
            this.year = registroServiciosPresenter.year
            this.fechaHoraTxt = registroServiciosPresenter.fechaHoraTxt
            fechaHoraTxt.visibility = View.VISIBLE
        }
    }

    fun eventoSeleccionarHora(){
        seleccionarHora.setOnClickListener{
            registroServiciosPresenter.pedirHora()
            this.hour = registroServiciosPresenter.hour
            this.minute = registroServiciosPresenter.minute
            this.fechaHoraTxt = registroServiciosPresenter.fechaHoraTxt
            fechaHoraTxt.visibility = View.VISIBLE
        }
    }
}