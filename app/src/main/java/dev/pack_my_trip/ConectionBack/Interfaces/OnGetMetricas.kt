package dev.pack_my_trip.ConectionBack.Interfaces

import dev.pack_my_trip.models.data_model.Servicio

interface OnGetMetricas {
    fun onGetMetricas(servicios: List<Servicio>)
}