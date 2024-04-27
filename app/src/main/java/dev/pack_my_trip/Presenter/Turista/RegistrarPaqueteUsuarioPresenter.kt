package dev.pack_my_trip.Presenter.Turista

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnRegistrarPaqueteUsuario
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnRegistrarPaqueteUsuarioPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository

class RegistrarPaqueteUsuarioPresenter {
    var repository : Repository = Repository()

    fun registrarPaqueteUsuario(correoUsuario : String, idPaquete: Int, context : Context, onRegistrarPaqueteUsuario: OnRegistrarPaqueteUsuario){
        repository.registrarUsuarioPaquete(correoUsuario, idPaquete, context, object:
            OnRegistrarPaqueteUsuarioPresenter {
            override fun onRegistrarPaqueteUsuarioPresenter(registrado: Boolean) {
                onRegistrarPaqueteUsuario.onRegistrarPaqueteUsuario(registrado)
            }
        })
    }
}