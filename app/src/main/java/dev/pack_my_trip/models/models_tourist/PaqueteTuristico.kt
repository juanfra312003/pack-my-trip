package dev.pack_my_trip.models.models_tourist
import java.io.Serializable

data class PaqueteTuristico (
    var nombre : String,
    var precio : Double,
    var nombreOrganizador : String,
    var tipo : String
) : Serializable{
    var picture : String = ""
    var servicios = mutableListOf<ServicioTuristico>()
}