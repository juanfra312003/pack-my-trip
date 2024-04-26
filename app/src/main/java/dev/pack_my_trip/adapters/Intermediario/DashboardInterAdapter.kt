package dev.pack_my_trip.adapters.Intermediario

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.squareup.picasso.Picasso
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.inter_activities.EditarPaqueteActivity
import dev.pack_my_trip.activities.inter_activities.ServiciosInter
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class DashboardInterAdapter(
    private val context: Context,
    private val data: List<PaqueteTuristico>,
    private val usuario: Usuario
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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertViewTemp = convertView
        if (convertViewTemp == null) {
            convertViewTemp = LayoutInflater.from(context).inflate(R.layout.adapter_dashboard_inter, parent, false) //Obtiene el xml con el diseño
        }
        val imagPaquete: ImageView = convertViewTemp!!.findViewById(R.id.imagenDashBoardAdapter) //Obtener imagen
        val nombrePaqueteEditableAgInt: TextView = convertViewTemp.findViewById(R.id.nombrePaqueteEditableAgInt)
        val editablePrecioAgInt: TextView = convertViewTemp.findViewById(R.id.editablePrecioAgInt)
        nombrePaqueteEditableAgInt.text = data[position].nombre
        editablePrecioAgInt.text = data[position].precioDolares.toString() + " $"
        val urlImg: String? = data[position].imagen
        if(urlImg != null && !urlImg.isEmpty()){
            Picasso.get().load(urlImg).placeholder(R.drawable.no_disponible).error(R.drawable.no_disponible).into(imagPaquete)
        }
        else{
            imagPaquete.setImageResource(R.drawable.no_disponible)
        }
        convertViewTemp.setOnClickListener{
            val intent = Intent(context, EditarPaqueteActivity::class.java) //Crea el intent con el contexto de esta actividad y la objetivo
            intent.putExtra("usuario", usuario)
            val lista = data[position].listaServicios
            if(lista != null){
                intent.putExtra("servicios", ArrayList(lista))
            }
            intent.putExtra("nombrePaquete", data[position].nombre)
            intent.putExtra("fecha", data[position].fechaHora)
            intent.putExtra("costo", data[position].precioDolares)
            val fechaHora = LocalDateTime.parse(data[position].fechaHora)
            intent.putExtra("dia", fechaHora.dayOfMonth)
            intent.putExtra("mes", fechaHora.monthValue)
            intent.putExtra("year", fechaHora.year)
            intent.putExtra("urlImagen", data[position].imagen)
            intent.putExtra("id", data[position].id)
            context.startActivity(intent) //Inicia la actividad
        }
        return convertViewTemp
    }
}