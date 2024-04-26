package dev.pack_my_trip.Presenter.General

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnEditarUsuario
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnEditarUsuarioPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.Usuario

class EditarUsuarioPresenter {
    var repository = Repository()

    fun editarUsuario(usuario: Usuario, context: Context, onEditarUsuario: OnEditarUsuario){
        repository.actualizarUsuario(usuario, context, object: OnEditarUsuarioPresenter {
            override fun onEditarUsuarioPresenter(realizado: Boolean) {
                onEditarUsuario.onEditarUsuario(realizado)
            }
        })
    }
}