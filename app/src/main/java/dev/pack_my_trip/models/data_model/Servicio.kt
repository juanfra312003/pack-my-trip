package dev.pack_my_trip.models.data_model
import java.io.Serializable
import java.sql.Blob
import java.time.LocalDateTime
import java.util.Date

data class Servicio (
    var id : Int,
    var nombre : String,
    var precio : Float,
    var limiteDiario : Int,
    var caracteristicas : String,
    var portada : String,
    var fechaHora : String,
    var correoOperador : String
)
    : Serializable
{
}