package dev.pack_my_trip.Presenter.General

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnCrearUsuario
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnCrearUsuarioPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.Usuario

class CrearUsuarioPresenter () {
    private var repository : Repository = Repository()

    fun registrarUsuario (usuario : Usuario, context : Context, onCrearUsuario : OnCrearUsuario) {
        repository.crearUsuario(usuario, context, object : OnCrearUsuarioPresenter {
            override fun onCrearUsuarioPresenter(exitoso: Boolean) {
                onCrearUsuario.onCrearUsuario(exitoso)
            }
        })
    }
}