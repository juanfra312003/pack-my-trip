package dev.pack_my_trip.ConectionBack.Interfaces

import dev.pack_my_trip.models.data_model.PaqueteTuristico

interface OnGetAgenda {
    fun onGetAgenda(paquetes: List<PaqueteTuristico>)
}