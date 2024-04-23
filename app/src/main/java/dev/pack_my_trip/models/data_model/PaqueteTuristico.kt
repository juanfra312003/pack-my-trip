package dev.pack_my_trip.models.data_model

import java.io.Serializable
import java.sql.Blob
import java.util.Date

data class PaqueteTuristico (
    var id : Int,
    var nombre : String,
    var fechaHora : String,
    var precioDolares : Float,
    var imagen : String,
    var listaServicios : List<Servicio>,
    var calificacion : Int,
    var comentarios : String,
    var comprobante : String
) : Serializable
{
}