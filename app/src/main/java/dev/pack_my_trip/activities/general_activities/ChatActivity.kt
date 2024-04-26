package dev.pack_my_trip.activities.general_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.tourist_activities.DashboardTouristActivity
import dev.pack_my_trip.activities.tourist_activities.TouristMapActivity
import dev.pack_my_trip.adapters.tourist_adapters.MessageAdapter
import dev.pack_my_trip.databinding.ActivityChatBinding
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista
import dev.pack_my_trip.models.models_tourist.messages.MessageApp
import dev.pack_my_trip.models.models_tourist.messages.TextMessage
import java.util.Date

class ChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatBinding
    private lateinit var paquete_turista : PaquetesPorTurista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        paquete_turista = intent.getSerializableExtra("paquete_turista") as PaquetesPorTurista

        // Manejar la barra de navegación
        manageNavBar()

        // Colocar el nombre del organizador en el textview
        binding.cFriendName.text = paquete_turista.paqueteActual.nombreOrganizador

        val messages = createMessages()

        val adapter = MessageAdapter(this, messages)
        binding.cmessages.adapter = adapter
    }

    private fun createMessages() : MutableList<MessageApp>{
        // Crear varios mensajes
        val mensajes = mutableListOf<MessageApp>(
            TextMessage(
                false, Date(), "Hola, ¿cómo estás?"
            ),
            TextMessage(
                true, Date(), "¡Hola! Bien, gracias."
            ),
            TextMessage(
                false, Date(), "¿Qué tal te parece el paquete turístico armado?"
            ),
            TextMessage(
                true, Date(), "¡Me encanta! Es perfecto para lo que necesito."
            ),
        )
        return mensajes
    }
    private fun manageNavBar(){
        binding.bottomNavigationViewTourist.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuBack -> {
                    startActivity(Intent(this, DashboardTouristActivity::class.java))
                    true
                }
                R.id.menuChat -> {
                    val intent = Intent(this, ChatActivity::class.java)
                    intent.putExtra("paquete_turista", paquete_turista)
                    startActivity(intent)
                    true
                }
                R.id.menuMap -> {
                    val intent = Intent(this, TouristMapActivity::class.java)
                    intent.putExtra("paquete_turista", paquete_turista)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}