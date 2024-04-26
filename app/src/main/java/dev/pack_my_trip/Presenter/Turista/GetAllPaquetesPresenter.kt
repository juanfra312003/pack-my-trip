package dev.pack_my_trip.Presenter.Turista

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetAllPaquetes
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetAllPaquetesPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.PaqueteTuristico

class GetAllPaquetesPresenter {
    private var repository: Repository = Repository()

    fun getAllPaquetes (context : Context, onGetAllPaquetes: OnGetAllPaquetes){
        repository.getAllPaquetesTuristicos(context, object: OnGetAllPaquetesPresenter {
            override fun onGetAllPaquetesPresenter(paquetes: List<PaqueteTuristico>) {
                onGetAllPaquetes.onGetAllPaquetes(paquetes)
            }
        })
    }
}