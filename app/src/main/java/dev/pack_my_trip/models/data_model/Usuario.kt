package dev.pack_my_trip.models.data_model
import java.io.Serializable
import java.util.Date

data class Usuario (
    var correo : String,
    var usuario : String,
    var contrasena : String,
    var tipo : Char
    ) : Serializable
{
    var fechaNacimiento : Date = Date()
    var latitud : Double = 0.0
    var longitud : Double = 0.0
    var region : String = ""
}

