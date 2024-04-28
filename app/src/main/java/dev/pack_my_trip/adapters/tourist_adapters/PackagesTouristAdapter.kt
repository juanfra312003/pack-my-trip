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
import dev.pack_my_trip.models.data_model.PaqueteTuristico
class PackagesTouristAdapter (context : Context, packages : MutableList<PaqueteTuristico>) : ArrayAdapter<PaqueteTuristico>(context, 0, packages) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.adapter_dashboard_tourist_packages, parent, false)
        }

        // Pintar los valores
        var description = itemView!!.findViewById<TextView>(R.id.textFieldDescriptionAdapterTourist)
        var begininningDate = itemView.findViewById<TextView>(R.id.textFieldDateAdapterTourist)
        var imagePackage = itemView.findViewById<ImageView>(R.id.imageAdapterDashBoardTourist)

        description.text = item!!.nombre
        begininningDate.text = item.fechaHora.toString()

        if (!item.imagen.isNullOrBlank()) {
            Picasso.get().load(item.imagen).into(imagePackage)
        } else {
            imagePackage.setImageResource(R.drawable.paquete_imagen)
        }

        return itemView
    }
}