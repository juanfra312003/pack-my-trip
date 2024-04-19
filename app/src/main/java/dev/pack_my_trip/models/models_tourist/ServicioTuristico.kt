package dev.pack_my_trip.models.models_tourist
import java.io.Serializable

data class ServicioTuristico (
    var nombre : String,
    var descripcion : String,
    var precio : Double
) : Serializable {
    var latitud : Double = 0.0
    var longitud : Double = 0.0
}