package dev.pack_my_trip.adapters.Intermediario

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.squareup.picasso.Picasso
import dev.pack_my_trip.R
import dev.pack_my_trip.models.data_model.Servicio

class AgregarServiciosAdapter (context : Context, var servicios : MutableList<Servicio>) : ArrayAdapter<Servicio>(context, 0, servicios) {
    var serviciosSeleccionados: MutableList<Servicio> = mutableListOf()

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.adapter_agregar_servicios, parent, false)
        }

        // Pintar los valores
        val description = itemView!!.findViewById<TextView>(R.id.descripcionAdapterSearchService)
        val organizerField = itemView.findViewById<TextView>(R.id.organizerTextAdapterSearchInter)
        val imageService = itemView.findViewById<ImageView>(R.id.imageAdapterSearchServices)
        itemView.setOnClickListener {
            if(!this@AgregarServiciosAdapter.serviciosSeleccionados.contains(item)){
                itemView.setBackgroundColor(Color.LightGray.toArgb())
                serviciosSeleccionados.add(item!!)
            }
            else{
                itemView.setBackgroundColor(Color.Transparent.toArgb())
                serviciosSeleccionados.remove(item!!)
            }
        }
        var urlImg: String? = item?.portada
        if(urlImg != null){
            urlImg = urlImg.trim()
        }
        if(urlImg != null && !urlImg.isEmpty()){
            Picasso.get().load(urlImg).into(imageService)
        }
        else{
            imageService.setImageResource(R.drawable.paquete_turistico_tursia)
        }
        description.text = item!!.nombre
        organizerField.text = item.correoOperador
        return itemView
    }
}