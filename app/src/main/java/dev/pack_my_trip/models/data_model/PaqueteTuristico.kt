package dev.pack_my_trip.models.data_model

import java.io.Serializable
import java.sql.Blob
import java.util.Date

data class PaqueteTuristico (
    var id : Int,
    var nombre : String,
    var precio : Double,
    var correoIntermediario : String
) : Serializable
{
  var fechaHora : Date = Date()
  var imagen : Blob? = null
}