package dev.pack_my_trip.Presenter.Intermediario

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServicios
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.Servicio

class AgregarServiciosPresenter {
    private var repository: Repository = Repository()

    fun getServicios(context: Context, onGetServicios: OnGetServicios){
        repository.getServicios(context, object: OnGetServicios{
            override fun onGetServicios(servicios: List<Servicio>) {
                onGetServicios.onGetServicios(servicios)
            }
        })
    }
}