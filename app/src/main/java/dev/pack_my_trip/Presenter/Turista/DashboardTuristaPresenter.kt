package dev.pack_my_trip.Presenter.Turista

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetPaquetesUsuario
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetPaquetesUsuarioPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.PaqueteTuristico

class DashboardTuristaPresenter {

    private var repository: Repository = Repository()

    fun getPaquetesUsuario(correoUsuario : String, context : Context, onGetPaquetesUsuario : OnGetPaquetesUsuario){
        repository.getPaquetesTuristicosUsuario(correoUsuario, context, object : OnGetPaquetesUsuarioPresenter {
            override fun onGetPaquetesUsuarioPresenter(paquetes : List<PaqueteTuristico>){
                onGetPaquetesUsuario.onGetPaquetesUsuario(paquetes)
            }
        })
    }
}