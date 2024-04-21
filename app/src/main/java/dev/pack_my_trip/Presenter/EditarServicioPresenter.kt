package dev.pack_my_trip.Presenter

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import dev.pack_my_trip.ConectionBack.OnCrearServicio
import dev.pack_my_trip.ConectionBack.OnEditarServicio
import dev.pack_my_trip.ConectionBack.OnEditarServicioPresenter
import dev.pack_my_trip.ConectionBack.Repository
import dev.pack_my_trip.models.data_model.Servicio
import java.time.Month
import java.util.Calendar

class EditarServicioPresenter(
    var fechaHoraTxt: TextView,
    var contexto: Context
) {
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var repository = Repository()

    fun pedirFecha() {
        val currentDate = Calendar.getInstance()
        year = currentDate.get(Calendar.YEAR)
        month = currentDate.get(Calendar.MONTH)
        day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(contexto,
            { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                if(year > this.year || (year == this.year && monthOfYear > this.month) ||
                    (year == this.year && monthOfYear == this.month && dayOfMonth >= this.day)) {
                    month = monthOfYear + 1
                    this.year = year
                    this.day = dayOfMonth
                    fechaHoraTxt.setText("El servicio se efectuará el "+ dayOfMonth + "/" + month + "/" + year )
                }
                else{
                    Toast.makeText(contexto, "La fecha es anterior a hoy", Toast.LENGTH_SHORT).show()
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    fun pedirHora(yearVal: Int, monthVal: Int, dayVal: Int) {
        this.year = yearVal
        this.month = monthVal
        this.day = dayVal
        val currentTime = Calendar.getInstance()
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        hour = currentTime.get(Calendar.HOUR_OF_DAY)
        minute = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(contexto,
            { _, hourOfDay, minute ->
                if(this.day != 0 && this.month != 0 && this.year != 0 &&
                    ((this.year > year || (year == this.year &&  this.month > month ) ||
                    (year == this.year && month == this.month &&  this.day >= day) ||
                    (hourOfDay > hour || (hourOfDay == hour && minute > this.minute))))) {
                    val texto: String = fechaHoraTxt.text.toString()
                    var fecha: String = texto
                    if(texto.contains("a las")){
                        fecha = texto.substring(0, texto.indexOf("a las") - 1)
                    }
                    this.hour = hourOfDay
                    this.minute = minute
                    fechaHoraTxt.setText(fecha + " a las " + hourOfDay + ":" + minute)
                }
                else{
                    Toast.makeText(contexto, "Seleccione la fecha o una hora mayor a la actual", Toast.LENGTH_SHORT).show()
                }
            },
            hour,
            minute,
            true // true si quieres que el formato de 24 horas esté habilitado
        )
        timePickerDialog.show()
    }

    fun editarServicio(servicio: Servicio, context: Context, onEditarServicio: OnEditarServicio){
        repository.editarServicio(servicio, context, object: OnEditarServicioPresenter{
            override fun onEditarServicioPresenter(realizado: Boolean) {
                onEditarServicio.onEditarServicio(realizado)
            }
        })
    }
}