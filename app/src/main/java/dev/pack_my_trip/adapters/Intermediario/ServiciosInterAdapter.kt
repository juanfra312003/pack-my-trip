package dev.pack_my_trip.adapters.Intermediario

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.operator_activities.EditarServicio
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario

class ServiciosInterAdapter (
    private val context: Context,
    var data: MutableList<Servicio>
) : BaseAdapter()  {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertViewTemp = convertView
        if (convertViewTemp == null) {
            convertViewTemp = LayoutInflater.from(context).inflate(R.layout.mis_servicios_adapter, parent, false) //Obtiene el xml con el diseño
        }
        convertViewTemp!!.setOnClickListener{
            data.removeAt(position)
            notifyDataSetChanged()
        }
        val textoServicio: TextView = convertViewTemp!!.findViewById(R.id.MisServiciosTxt) //Obtener texto
        val imagServicio: ImageView = convertViewTemp!!.findViewById(R.id.MisServiciosImgView) //Obtener imagen]
        textoServicio.text = data[position].nombre
        var urlImg: String? = data[position].portada

        if(urlImg != null && !urlImg.isEmpty()){
            Picasso.get().load(urlImg).into(imagServicio)
        }
        else{
            imagServicio.setImageResource(R.drawable.no_disponible)
        }

        return convertViewTemp
    }
}