package dev.pack_my_trip.activities.operator_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dev.pack_my_trip.Presenter.EditarServicioPresenter
import dev.pack_my_trip.Presenter.RegistroServiciosPresenter
import dev.pack_my_trip.R

class EditarServicio : AppCompatActivity() {
    private lateinit var aceptarCambiosBtn: Button
    private lateinit var caracteristicasETxt: EditText
    private lateinit var nombreServicioETxt : EditText
    private lateinit var seleccionarHora: Button
    private lateinit var seleccionarFecha: Button
    private lateinit var editarServicioPresenter: EditarServicioPresenter
    private lateinit var fechaHoraTxt: TextView
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_servicio)
        inicializarVariables()
        editarServicioPresenter = EditarServicioPresenter(fechaHoraTxt, this);
        eventoRegistarServicio()
        eventoSeleccionarFecha()
        eventoSeleccionarHora()
    }

    fun inicializarVariables(){
        aceptarCambiosBtn = findViewById(R.id.aceptarCambiosBtn)
        caracteristicasETxt = findViewById(R.id.caracteristicasEdtETxt)
        caracteristicasETxt.setSelection(0)
        caracteristicasETxt.gravity = Gravity.START or Gravity.TOP
        nombreServicioETxt = findViewById(R.id.nombreServicioEdtETxt)
        nombreServicioETxt.isEnabled = false
        nombreServicioETxt.setText("Experiencia Acuática Playa San Carlos.")
        seleccionarHora = findViewById(R.id.seleccionarHoraEdtBtn)
        seleccionarFecha = findViewById(R.id.seleccionarFechaEdtBtn)
        fechaHoraTxt = findViewById(R.id.fechaHoraEdtTxt)
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