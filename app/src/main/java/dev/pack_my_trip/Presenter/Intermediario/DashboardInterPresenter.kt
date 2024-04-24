package dev.pack_my_trip.Presenter.Intermediario

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetPaquetes
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetPaquetesPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.PaqueteTuristico

class DashboardInterPresenter {
    private var repository: Repository = Repository()

    fun getPaquetes(correoIntermediario: String, context: Context, onGetPaquetes: OnGetPaquetes){
        repository.getPaquetesTuristicos(correoIntermediario, context, object:
            OnGetPaquetesPresenter {
            override fun onGetPaquetesPresenter(paquetes: List<PaqueteTuristico>) {
                onGetPaquetes.onGetPaquetes(paquetes)
            }
        })
    }
}