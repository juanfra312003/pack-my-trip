package dev.pack_my_trip.adapters.Intermediario

import android.annotation.SuppressLint
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
import dev.pack_my_trip.models.models_tourist.PaqueteTuristico

class AgregarServiciosAdapter (context : Context, packages : MutableList<Servicio>) : ArrayAdapter<Servicio>(context, 0, packages) {
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.adapter_search_tourist_packages, parent, false)
        }

        // Pintar los valores
        val description = itemView!!.findViewById<TextView>(R.id.descripcionAdapterSearchPackage)
        val organizerField = itemView.findViewById<TextView>(R.id.organizerAdapterSearchTourist)
        val imageService = itemView.findViewById<ImageView>(R.id.imageAdapterSearchPackages)
        val urlImg: String? = item?.portada
        if(urlImg != null && !urlImg.isEmpty()){
            Picasso.get().load(urlImg).placeholder(R.drawable.no_disponible).error(R.drawable.no_disponible).into(imageService)
        }
        else{
            imageService.setImageResource(R.drawable.no_disponible)
        }
        description.text = item!!.nombre
        organizerField.text = organizerField.text.toString() + " " + item.correoOperador
        return itemView
    }
}