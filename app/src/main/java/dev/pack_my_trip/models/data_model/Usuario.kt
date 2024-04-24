package dev.pack_my_trip.models.data_model
import java.io.Serializable
import java.sql.Blob
import java.util.Date

data class Usuario (
    var correo : String,
    var usuario : String,
    var contrasena : String,
    var fechaNacimiento : String,
    var latitud : Float,
    var longitud : Float,
    var region : String,
    var tipo : Char,
    var fotoPerfil : String,
    var listaPaquetes : List<PaqueteTuristico>
    ) : Serializable
{
}