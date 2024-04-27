package dev.pack_my_trip.Presenter.Turista

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetServiciosPaquete
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetServiciosPaquetePresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.Servicio

class PackageSearchPresenter {
    private var repository: Repository = Repository()

    fun getServiciosPaquete (idPaquete : Int, context : Context, onGetServiciosPaquete: OnGetServiciosPaquete) {
        repository.getServiciosPaquete(idPaquete, context, object : OnGetServiciosPaquetePresenter {
            override fun onGetServiciosPaquetePresenter(servicios: List<Servicio>) {
                onGetServiciosPaquete.onGetServiciosPaquete(servicios)
            }
        })
    }
}