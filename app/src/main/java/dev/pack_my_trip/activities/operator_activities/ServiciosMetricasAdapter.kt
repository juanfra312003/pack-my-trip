package dev.pack_my_trip.activities.operator_activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import dev.pack_my_trip.R

class ServiciosMetricasAdapter(
    private val context: Context,
    private val data: Map<String, Int>) : BaseAdapter()  {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        var lista = data.toList()
        return lista[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertViewTemp = convertView
        if (convertViewTemp == null) {
            convertViewTemp = LayoutInflater.from(context).inflate(R.layout.mis_servicios_adapter, parent, false) //Obtiene el xml con el diseño
        }
        //val textoServicio: TextView = convertViewTemp!!.findViewById(R.id.MisServiciosTxt) //Obtener texto
        //val imagServicio: ImageView = convertViewTemp!!.findViewById(R.id.MisServiciosImgView) //Obtener imagen
        convertViewTemp!!.setOnClickListener{
            val intent = Intent(context, DashboardOperator::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            context.startActivity(intent) //Inicia la actividad
        }
        return convertViewTemp
    }
}