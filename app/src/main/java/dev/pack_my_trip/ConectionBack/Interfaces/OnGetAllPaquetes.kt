package dev.pack_my_trip.ConectionBack.Interfaces

import dev.pack_my_trip.models.data_model.PaqueteTuristico

interface OnGetAllPaquetes {
    fun onGetAllPaquetes(paquetes: List<PaqueteTuristico>)
}