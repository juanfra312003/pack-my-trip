package dev.pack_my_trip.models.data_model

import java.io.Serializable
import java.sql.Blob

data class UsuarioXPaqueteTuristico (
    var usuario : Usuario,
    var paqueteTuristico : PaqueteTuristico
) : Serializable
{
    var calificacion : Int = -1
    var comentarios : String = ""
    var comprobante : Blob? = null
}