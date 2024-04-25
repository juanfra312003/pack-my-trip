package dev.pack_my_trip.Presenter.Intermediario

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import dev.pack_my_trip.ConectionBack.Interfaces.OnCrearPaquete
import dev.pack_my_trip.ConectionBack.Interfaces.OnCrearServicio
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
import java.util.Calendar

class ServiciosInterPresenter(var contexto: Context, var fechaHoraTxt: TextView) {

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

    fun crearPaquete(paquete: PaqueteTuristico, context: Context, onCrearPaquete: OnCrearPaquete){

    }
}