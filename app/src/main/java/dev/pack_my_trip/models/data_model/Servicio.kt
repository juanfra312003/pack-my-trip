package dev.pack_my_trip.models.data_model
import java.io.Serializable
import java.sql.Blob
import java.util.Date

data class Servicio (
    var id : Int,
    var nombre : String,
    var precio : Double,
    var limiteDiario : Int,
    var descripcion : String,
    var operador : String
)
    : Serializable
{
    var latitud : Double = 0.0
    var longitud : Double = 0.0
    var fechaHora : Date = Date()
    var portada : Blob? = null
}