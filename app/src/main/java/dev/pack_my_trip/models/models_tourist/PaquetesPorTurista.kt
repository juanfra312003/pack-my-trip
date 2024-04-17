package dev.pack_my_trip.models.models_tourist

import java.io.Serializable
import java.util.Date
import java.util.UUID

data class PaquetesPorTurista (
    var uuid_user: UUID,
    var paqueteActual : PaqueteTuristico,
    var fecha : Date
) : Serializable{
    var calificacion = 0
    var comentario = ""
}