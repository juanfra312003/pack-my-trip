package dev.pack_my_trip.adapters.tourist_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import dev.pack_my_trip.R
import dev.pack_my_trip.models.models_tourist.PaqueteTuristico

class PackagesSearchAdapter (context : Context, packages : MutableList<PaqueteTuristico>) : ArrayAdapter<PaqueteTuristico>(context, 0, packages) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.adapter_search_tourist_packages, parent, false)
        }

        // Pintar los valores
        var description = itemView!!.findViewById<TextView>(R.id.descripcionAdapterSearchPackage)
        var organizerField = itemView.findViewById<TextView>(R.id.organizerAdapterSearchTourist)
        var imagePackage = itemView.findViewById<ImageView>(R.id.imageAdapterSearchPackages)

        description.text = item!!.nombre
        organizerField.text =  "Organizador: " + item.nombreOrganizador

        // TODO: Realizar la modificaciòn de la imagen con Firebase Storage en la parte de backend
        //imagePackage.setImageResource(item.paqueteActual.picture)
        when (item.tipo) {
            "Volcan" -> {
                imagePackage.setImageResource(R.drawable.volcan)
            }
            "Aviario" -> {
                imagePackage.setImageResource(R.drawable.aviario)
            }
            "Buceo" -> {
                imagePackage.setImageResource(R.drawable.buceo)
            }
            else -> {
                imagePackage.setImageResource(R.drawable.paquete_general)
            }
        }

        return itemView
    }
}