package dev.pack_my_trip.models.models_tourist

import java.io.Serializable
import java.util.UUID

data class Turista(
    var nombre : String,
    var correo : String,
) : Serializable {
    var uuid: UUID = UUID.randomUUID()
    var paquetes = mutableListOf<PaquetesPorTurista>()
    var latitud : Double = 0.0
    var longitud : Double = 0.0
}