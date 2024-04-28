package dev.pack_my_trip.Presenter.Intermediario

import android.content.Context
import dev.pack_my_trip.ConectionBack.Interfaces.OnGetAgenda
import dev.pack_my_trip.ConectionBack.InterfacesPresenter.OnGetAgendaPresenter
import dev.pack_my_trip.ConectionBack.Repository.Repository
import dev.pack_my_trip.models.data_model.PaqueteTuristico

class AgendaPaquetesPresenter {
    private var repository: Repository = Repository()

    fun getPaquetes(correoIntermediario: String, context: Context, onGetAgenda: OnGetAgenda){
        repository.getAgendaInter(correoIntermediario, context, object:
            OnGetAgendaPresenter {
            override fun onGetAgendaPresenter(paquetes: List<PaqueteTuristico>) {
                onGetAgenda.onGetAgenda(paquetes)
            }
        })
    }
}