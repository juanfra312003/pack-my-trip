package dev.pack_my_trip.Presenter.General

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetUsuario
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetUsuarioPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.Usuario

class LogInPresenter {
    private var repository : Repository = Repository()

    fun getUsuario (correo:String, contrasena : String, context : Context, onGetUsuario : OnGetUsuario){
        repository.getUsuario(correo, contrasena, context, object : OnGetUsuarioPresenter {
            override fun onGetUsuarioPresenter(usuario : Usuario){
                onGetUsuario.onGetUsuario(usuario)
            }
        })
    }
}