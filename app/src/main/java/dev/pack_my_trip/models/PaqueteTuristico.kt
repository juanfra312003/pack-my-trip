package dev.pack_my_trip.models

import dev.pack_my_trip.models.data_model.Servicio
import java.io.Serializable

data class PaqueteTuristico (
    var nombre : String,
) : Serializable{
    var picture : String = ""
    var servicios = mutableListOf<Servicio>()
}