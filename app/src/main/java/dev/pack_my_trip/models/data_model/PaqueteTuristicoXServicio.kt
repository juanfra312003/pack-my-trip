package dev.pack_my_trip.models.data_model

import java.io.Serializable
import java.sql.Time

data class PaqueteTuristicoXServicio (
    var paqueteTuristico : PaqueteTuristico,
    var servicioTuristico : Servicio,
): Serializable
{
    var tiempoEstancia : Time = Time(0)
}