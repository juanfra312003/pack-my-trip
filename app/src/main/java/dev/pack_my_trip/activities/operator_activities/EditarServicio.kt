package dev.pack_my_trip.activities.operator_activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import dev.pack_my_trip.Presenter.EditarServicioPresenter
import dev.pack_my_trip.Presenter.RegistroServiciosPresenter
import dev.pack_my_trip.R
import dev.pack_my_trip.models.data_model.Servicio

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
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_servicio)
        inicializarVariables()
        editarServicioPresenter = EditarServicioPresenter(fechaHoraTxt, this);
        servicio = intent.getSerializableExtra("servicio") as Servicio
        eventoRegistarServicio()
        eventoSeleccionarFecha()
        eventoSeleccionarHora()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun inicializarVariables(){
        aceptarCambiosBtn = findViewById(R.id.aceptarCambiosBtn)
        caracteristicasETxt = findViewById(R.id.caracteristicasEdtETxt)
        caracteristicasETxt.setSelection(0)
        caracteristicasETxt.gravity = Gravity.START or Gravity.TOP
        caracteristicasETxt.setText(servicio.descripcion)
        nombreServicioETxt = findViewById(R.id.nombreServicioEdtETxt)
        nombreServicioETxt.isEnabled = false
        nombreServicioETxt.setText(servicio.nombre)
        seleccionarHora = findViewById(R.id.seleccionarHoraEdtBtn)
        seleccionarFecha = findViewById(R.id.seleccionarFechaEdtBtn)
        fechaHoraTxt = findViewById(R.id.fechaHoraEdtTxt)
        fechaHoraTxt.text = "El servicio se efectuará el "+ servicio.fechaHora.dayOfMonth + "/" + servicio.fechaHora.month + "/" + servicio.fechaHora.year + " a las "+
                servicio.fechaHora.hour + ":" + servicio.fechaHora.minute
        precioEditText = findViewById(R.id.precioServicioEdtETxt)
        precioEditText.setText(servicio.precio.toString())
        limiteDiarioEditText = findViewById(R.id.limiteServicioEdtETxt)
        limiteDiarioEditText.setText(servicio.limiteDiario.toString())
        portadaImgView = findViewById(R.id.portadaEdtImgView)
        /*var byte = servicio.portada.toByteArray(Charsets.UTF_8)
        val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
        portadaImgView.setImageBitmap(bitmap)*/
    }

    fun eventoRegistarServicio(){
        aceptarCambiosBtn.setOnClickListener{
            val intent = Intent(this, DashboardOperator::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            startActivity(intent) //Inicia la actividad
        }
    }

    fun eventoSeleccionarFecha(){
        seleccionarFecha.setOnClickListener{
            editarServicioPresenter.pedirFecha()
            this.day = editarServicioPresenter.day
            this.month = editarServicioPresenter.month
            this.year = editarServicioPresenter.year
            this.fechaHoraTxt = editarServicioPresenter.fechaHoraTxt
            fechaHoraTxt.visibility = View.VISIBLE
        }
    }

    fun eventoSeleccionarHora(){
        seleccionarHora.setOnClickListener{
            editarServicioPresenter.pedirHora()
            this.hour = editarServicioPresenter.hour
            this.minute = editarServicioPresenter.minute
            this.fechaHoraTxt = editarServicioPresenter.fechaHoraTxt
            fechaHoraTxt.visibility = View.VISIBLE
        }
    }
}