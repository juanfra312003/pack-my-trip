package dev.pack_my_trip.Presenter.General

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnEditarRegionUsuario
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnEditarRegionUsuarioPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.Usuario

class EditarRegionUsuarioPresenter {
    var repository = Repository()

    fun editarRegionUsuario (usuario: Usuario, context : Context, onEditarRegionUsuario : OnEditarRegionUsuario) {
        repository.actualizarRegion(usuario, context, object :
            OnEditarRegionUsuarioPresenter {
            override fun onEditarRegionUsuarioPresenter(realizado: Boolean) {
                onEditarRegionUsuario.onEditarRegionUsuario(realizado)
            }
        })
    }
}