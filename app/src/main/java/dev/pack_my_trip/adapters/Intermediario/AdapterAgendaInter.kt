package dev.pack_my_trip.adapters.Intermediario

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
import kotlin.random.Random

class AdapterAgendaInter (context : Context, packages : MutableList<PaqueteTuristico>) : ArrayAdapter<PaqueteTuristico>(context, 0, packages) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)

        if (itemView == null) {
            itemView =
                LayoutInflater.from(context).inflate(R.layout.adapter_agenda_inter, parent, false)
        }

        var nombre = itemView!!.findViewById<TextView>(R.id.nombrePaqueteAdapterAgenda)
        var fechaInicio = itemView!!.findViewById<TextView>(R.id.textEditableFechaInicioAgendaInt)
        var turistaField = itemView.findViewById<TextView>(R.id.textViewTuristaAgenda)
        var imagePackage = itemView.findViewById<ImageView>(R.id.imageViewAdapterAgendaInter)

        val lista = listOf(
            "Juan Francisco",
            "Julian",
            "Pedro",
            "Maria",
            "Jose",
            "Luis",
            "Ana",
            "Sofia",
            "Carlos",
            "Fernando",
            "Ricardo"
        )
        turistaField.text = lista[Random.nextInt(0, lista.size)]
        nombre.text = item!!.nombre
        fechaInicio.text = item.fechaHora

        if (!item.imagen.isNullOrBlank()) {
            Picasso.get().load(item.imagen).into(imagePackage)
        } else {
            imagePackage.setImageResource(R.drawable.paquete_imagen)
        }

        return itemView
    }
}