package dev.pack_my_trip.Presenter.Intermediario

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetAllServicios
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServicios
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetAllServiciosPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.Servicio

class AgregarServiciosPresenter {
    private var repository: Repository = Repository()

    fun getAllServicios(context: Context, onGetAllServicios: OnGetAllServicios){
        repository.getAllServicios(context, object: OnGetAllServiciosPresenter{
            override fun onGetAllServiciosPresenter(servicios: List<Servicio>) {
                onGetAllServicios.onGetAllServicios(servicios)
            }
        })
    }
}