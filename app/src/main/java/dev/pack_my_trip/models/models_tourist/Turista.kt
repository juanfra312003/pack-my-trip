package dev.pack_my_trip.models.models_tourist

import java.io.Serializable
import java.util.UUID
import android.content.Context
import dev.pack_my_trip.R


data class Turista(
    var nombre: String,
    var correo: String,
    var contraseña: String,
    var diaNacimiento: Int,
    var mesNacimiento: Int,
    var añoNacimiento: Int,
    var rol: String
) : Serializable {
    var uuid: UUID = UUID.randomUUID()
    var paquetes = mutableListOf<PaquetesPorTurista>()
    var latitud: Double = 0.0
    var longitud: Double = 0.0

    fun obtenerFechaNacimientoCompleta(): String {
        return "$diaNacimiento/$mesNacimiento/$añoNacimiento"
    }

    fun guardarUsuarioLogueado(usuario: Turista, context: Context) {
        val sharedPreferences = context.getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("nombre", usuario.nombre)
            putString("correo", usuario.correo)
            putString("contraseña", usuario.contraseña)
            putInt("diaNacimiento", usuario.diaNacimiento)
            putInt("mesNacimiento", usuario.mesNacimiento)
            putInt("añoNacimiento", usuario.añoNacimiento)
            putString("rol", usuario.rol)
            apply()
        }
    }

    fun obtenerUsuarioLogueado(context: Context): Turista? {
        val sharedPreferences = context.getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("nombre")) {
            return null  // No hay usuario logueado
        }

        return Turista(
            nombre = sharedPreferences.getString("nombre", "")!!,
            correo = sharedPreferences.getString("correo", "")!!,
            contraseña = sharedPreferences.getString("contraseña", "")!!,
            diaNacimiento = sharedPreferences.getInt("diaNacimiento", 0),
            mesNacimiento = sharedPreferences.getInt("mesNacimiento", 0),
            añoNacimiento = sharedPreferences.getInt("añoNacimiento", 0),
            rol = sharedPreferences.getString("rol", "")!!
        )
    }


}

