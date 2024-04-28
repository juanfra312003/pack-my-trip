package dev.pack_my_trip.activities.tourist_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.tourist_adapters.MessageAdapter
import dev.pack_my_trip.databinding.ActivityChatTouristBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.messages.MessageApp
import dev.pack_my_trip.models.models_tourist.messages.TextMessage
import java.util.Date

class ChatTouristActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatTouristBinding
    private lateinit var paquete_turista : PaqueteTuristico
    private lateinit var usuario : Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        paquete_turista = intent.getSerializableExtra("paquete_turista") as PaqueteTuristico
        usuario = intent.getSerializableExtra("usuario") as Usuario
        cargarValores()
        manageNavBar()
    }

    private fun cargarValores(){
        binding.cFriendName.text = paquete_turista.correoIntermediario
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
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuBack -> {
                    val intent = Intent(this, DashboardTouristActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete_turista", paquete_turista)
                    startActivity(intent)
                    true
                }
                R.id.menuChat -> {
                    val intent = Intent(this, ChatTouristActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete_turista", paquete_turista)
                    startActivity(intent)
                    true
                }
                R.id.menuMap -> {
                    val intent = Intent(this, TouristMapActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete_turista", paquete_turista)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}