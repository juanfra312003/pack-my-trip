package dev.pack_my_trip.Presenter

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
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

    fun pedirHora() {
        val currentTime = Calendar.getInstance()
        hour = currentTime.get(Calendar.HOUR_OF_DAY)
        minute = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(contexto,
            { _, hourOfDay, minute ->
                if(day != 0 && month != 0 && year != 0 &&
                    (hourOfDay > hour || (hourOfDay == hour && minute > this.minute))) {
                    var texto: String = fechaHoraTxt.text.toString()
                    fechaHoraTxt.setText(texto + " a las " + hourOfDay + ":" + minute)
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
}