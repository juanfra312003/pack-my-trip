package dev.pack_my_trip.models.data_model

import java.io.Serializable
import java.util.Date

data class Mensaje (
    var id : Int,
    var usuario1 : Usuario,
    var usuario2 : Usuario,
    var mensaje : String,
    var enviado : Boolean,
    var hora : Date
) : Serializable{
}