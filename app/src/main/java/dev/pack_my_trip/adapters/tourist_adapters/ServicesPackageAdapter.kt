package dev.pack_my_trip.adapters.tourist_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dev.pack_my_trip.R
import dev.pack_my_trip.models.models_tourist.ServicioTuristico

class ServicesPackageAdapter  (context : Context, services : MutableList<ServicioTuristico>) : ArrayAdapter<ServicioTuristico>(context, 0, services) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.adapter_services_package, parent, false)
        }

        // Pintar los valores
        val description = itemView!!.findViewById<TextView>(R.id.descripcionServiceTextViewEditable)
        description.text = item!!.descripcion

        return itemView
    }
}