package dev.pack_my_trip.activities.operator_activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import dev.pack_my_trip.R
import dev.pack_my_trip.models.data_model.Servicio
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class ServiciosMetricasAdapter(private val context: Activity, private val servicios: List<Servicio>)
    : ArrayAdapter<Servicio>(context, 0, servicios) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_servicios_metricas, null, true)
        val llFirst = rowView.findViewById(R.id.llFirst) as LinearLayout
        val llSecond = rowView.findViewById(R.id.llSecond) as LinearLayout
        val nombreServicio = rowView.findViewById(R.id.nombreServicio) as TextView
        val tasaOcupacion = rowView.findViewById(R.id.tasaOcupacion) as TextView
        val duracionPromedio = rowView.findViewById(R.id.duracionPromedio) as TextView
        val ingresosGenerados = rowView.findViewById(R.id.ingresosGenerados) as TextView
        val indiceVistas = rowView.findViewById(R.id.indiceVisitas) as TextView
        val plan = rowView.findViewById(R.id.plan) as TextView
        val fecha = rowView.findViewById(R.id.fecha) as TextView
        val image = rowView.findViewById(R.id.arrow) as ImageView

        nombreServicio.text = servicios[position].nombre
        tasaOcupacion.text = servicios[position].tasaOcupacion.toString()
        ingresosGenerados.text = servicios[position].ingresos.toString()
        indiceVistas.text = servicios[position].indiceRepeticion.toString()
        duracionPromedio.text = servicios[position].horasPromedio
        llSecond.visibility = View.GONE
        val fechaDate = LocalDateTime.parse(servicios[position].fechaHora)
        fecha.text = fechaDate.dayOfMonth.toString() + "/" + fechaDate.monthValue + "/" + fechaDate.year
        plan.text = servicios[position].nombre
        val imagen = rowView.findViewById(R.id.icon) as ImageView
        var urlImg = servicios[position].portada
        if(urlImg != null){
            urlImg = urlImg.trim()
        }
        if(urlImg != null && !urlImg.isEmpty()){
            Picasso.get().load(urlImg).placeholder(R.drawable.no_disponible).error(R.drawable.no_disponible).into(imagen)
        }
        else{
            imagen.setImageResource(R.drawable.no_disponible)
        }
        llFirst.setOnClickListener{
            if (llSecond.isVisible) {
                llSecond.visibility = View.GONE
                image.rotation = 0f
            } else {
                llSecond.visibility = View.VISIBLE
                image.rotation = 180.0f
            }
        }

        return rowView
    }
}