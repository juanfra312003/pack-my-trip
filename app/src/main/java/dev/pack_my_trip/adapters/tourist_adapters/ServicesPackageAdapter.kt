package dev.pack_my_trip.adapters.tourist_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import dev.pack_my_trip.R
import dev.pack_my_trip.models.data_model.Servicio

class ServicesPackageAdapter  (context : Context, services : MutableList<Servicio>) : ArrayAdapter<Servicio>(context, 0, services) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.adapter_services_package, parent, false)
        }

        val description = itemView!!.findViewById<TextView>(R.id.descripcionServiceTextViewEditable)
        val image = itemView!!.findViewById<ImageView>(R.id.service_tourism_imageview)
        description.text = item!!.nombre
        if (!item.portada.isNullOrBlank()) {
            Picasso.get().load(item.portada).into(image)
        } else {
            image.setImageResource(R.drawable.servicio_turistico)
        }
        return itemView
    }
}