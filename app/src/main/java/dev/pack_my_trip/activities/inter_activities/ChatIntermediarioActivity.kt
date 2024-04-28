package dev.pack_my_trip.activities.inter_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pack_my_trip.R
import dev.pack_my_trip.adapters.tourist_adapters.MessageAdapter
import dev.pack_my_trip.databinding.ActivityChatIntermediarioBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Usuario
import dev.pack_my_trip.models.models_tourist.messages.MessageApp
import dev.pack_my_trip.models.models_tourist.messages.TextMessage
import java.util.Date

class ChatIntermediarioActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatIntermediarioBinding
    private lateinit var usuario : Usuario
    private lateinit var paquete : PaqueteTuristico
    private lateinit var nombre : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatIntermediarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        paquete = intent.getSerializableExtra("paquete") as PaqueteTuristico
        nombre = intent.getStringExtra("nombre").toString()

        cargarValores()
        manejoBarraNavegacion()
    }

    private fun cargarValores(){
        binding.cFriendName.text = nombre
        val messages = crearMensajes()
        val adapter = MessageAdapter(this, messages)
        binding.cmessages.adapter = adapter
    }

    private fun crearMensajes() : MutableList<MessageApp>{
        // Crear varios mensajes
        val mensajes = mutableListOf<MessageApp>(
            TextMessage(
                true, Date(), "Hola, ¿cómo estás?"
            ),
            TextMessage(
                false, Date(), "¡Hola! Bien, gracias."
            ),
            TextMessage(
                true, Date(), "¿Qué tal te parece el paquete turístico armado?"
            ),
            TextMessage(
                false, Date(), "¡Me encanta! Es perfecto para lo que necesito."
            ),
            TextMessage(
                true, Date(), "¡Me alegra que te guste, te espero en CR. PURA VIDA MAE!"
            )
        )
        return mensajes
    }
    private fun manejoBarraNavegacion(){
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuChat -> {
                    val intent = Intent(baseContext, ChatIntermediarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    intent.putExtra("nombre", nombre)
                    startActivity(intent)
                    true
                }
                R.id.menuMap -> {
                    val intent = Intent(baseContext, FollowTouristActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    intent.putExtra("nombre", nombre)
                    startActivity(intent)
                    true
                }
                R.id.menuBack -> {
                    val intent = Intent(baseContext, AgendaIntermediarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("paquete", paquete)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}