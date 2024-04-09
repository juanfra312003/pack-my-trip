package dev.pack_my_trip.models.models_tourist
import java.io.Serializable

data class ServicioTuristico (
    var nombre : String,
    var latitud : Double,
    var longitud : Double,
    var descripcion : String
) : Serializable {
    var precio : Double = 0.0
}