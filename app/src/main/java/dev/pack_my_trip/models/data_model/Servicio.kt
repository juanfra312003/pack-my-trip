package dev.pack_my_trip.models.data_model
import java.io.Serializable

data class Servicio (
    var id : Int,
    var nombre : String,
    var precio : Float,
    var limiteDiario : Int,
    var caracteristicas : String,
    var portada : String,
    var fechaHora : String,
    var correoOperador : String,
    var tasaOcupacion: Float,
    var horasPromedio: String,
    var ingresos: Float,
    var indiceRepeticion: Float = 0.0f
)
    : Serializable
{

}