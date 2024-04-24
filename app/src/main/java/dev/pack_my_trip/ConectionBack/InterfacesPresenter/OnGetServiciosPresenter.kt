package dev.pack_my_trip.ConectionBack.InterfacesPresenter

import dev.pack_my_trip.models.data_model.Servicio

fun interface OnGetServiciosPresenter {
    fun onGetServiciosPresenter(servicios: List<Servicio>)
}