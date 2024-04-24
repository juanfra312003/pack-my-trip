package dev.pack_my_trip.Presenter.Operador

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServicios
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetServiciosPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.Servicio

class DashboardOperatorPresenter {
    private var repository: Repository = Repository()

    fun getServicios(correoOperador: String, context: Context, onGetServicios: OnGetServicios){
        repository.getServicios(correoOperador, context, object: OnGetServiciosPresenter {
            override fun onGetServiciosPresenter(servicios: List<Servicio>) {
                onGetServicios.onGetServicios(servicios)
            }
        })
    }
}