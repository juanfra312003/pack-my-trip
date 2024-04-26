package dev.pack_my_trip.Presenter.Turista

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnActualizarComentariosCalificaciones
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnActualizarComentariosCalificacionesPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository

class ActualizarComentariosCalificacionesPresenter {
    var repository : Repository = Repository()

    fun actualizarComentariosCalificaciones(idPaquete: Int, correoUsuario : String, comentarios : String, calificacion : Int, context : Context, onActualizarComentariosCalificaciones: OnActualizarComentariosCalificaciones){
        repository.actualizarComentariosCalificaciones(idPaquete, correoUsuario, comentarios, calificacion, context, object: OnActualizarComentariosCalificacionesPresenter {
            override fun onActualizarComentariosCalificacionesPresenter(realizado : Boolean) {
                onActualizarComentariosCalificaciones.onActualizarComentariosCalificaciones(realizado)
            }
        })
    }
}