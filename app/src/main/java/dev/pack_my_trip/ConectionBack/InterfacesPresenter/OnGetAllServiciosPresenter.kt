package dev.pack_my_trip.ConectionBack.InterfacesPresenter

import dev.pack_my_trip.models.data_model.Servicio

interface OnGetAllServiciosPresenter {
    fun onGetAllServiciosPresenter(servicios: List<Servicio>)
}