package dev.pack_my_trip.ConectionBack.InterfacesPresenter

import dev.pack_my_trip.models.data_model.PaqueteTuristico

interface OnGetAgendaPresenter {
    fun onGetAgendaPresenter(paquetes: List<PaqueteTuristico>)
}