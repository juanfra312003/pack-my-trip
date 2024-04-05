package dev.pack_my_trip.models

import java.io.Serializable

data class PaqueteTuristico (
    var nombre : String,
) : Serializable{
    var picture : String = ""
    var servicios = mutableListOf<ServicioTuristico>()
}