package dev.pack_my_trip.activities.inter_activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.MenuItem.OnMenuItemClickListener
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import dev.pack_my_trip.ConectionBack.Interfaces.OnCrearPaquete
import dev.pack_my_trip.ConectionBack.Interfaces.OnUpdatePaquete
import dev.pack_my_trip.Presenter.Intermediario.EditarPaquetePresenter
import dev.pack_my_trip.Presenter.Intermediario.ServiciosInterPresenter
import dev.pack_my_trip.Presenter.Operador.OnSubirImagen
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.general_activities.ChatActivity
import dev.pack_my_trip.adapters.Intermediario.AgregarServiciosAdapter
import dev.pack_my_trip.adapters.Intermediario.ServiciosInterAdapter
import dev.pack_my_trip.databinding.ActivityEditarPaqueteBinding
import dev.pack_my_trip.databinding.ActivityServiciosInterBinding
import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import kotlin.random.Random

class EditarPaqueteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditarPaqueteBinding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var usuario: Usuario
    private lateinit var servicios: MutableList<Servicio>
    private lateinit var editarPaquetePresenter: EditarPaquetePresenter
    private lateinit var serviciosInterAdapter: ServiciosInterAdapter
    private var bitmapPortada: Bitmap? = null
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var id: Int = 0
    private var urlImagen: String? = null
    private var fechaPuesta: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPaqueteBinding.inflate(layoutInflater)
        usuario = intent.getSerializableExtra("usuario") as Usuario
        servicios = listOf<Servicio>().toMutableList()
        setContentView(binding.root)
        firebaseStorage = FirebaseStorage.getInstance()
        inicializarVariables()
        onSubirImagen()
        eventoActualizarPaquete()
        eventoSeleccionarFecha()
        eventoAgregarServicio()
        eventoChat()
        eventBack()
        seguirPaquete()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun inicializarVariables(){
        usuario = intent.getSerializableExtra("usuario") as Usuario
        id = intent.getSerializableExtra("id") as Int
        binding.organizadorTextEditablePackageT.text = usuario.correo
        val nombrePaquete = intent.getSerializableExtra("nombrePaquete") as String
        binding.nombrePaqueteEtxt.setText(nombrePaquete)
        val fecha = intent.getSerializableExtra("fecha") as String
        if(!fecha.contains("El")){
            val fechaHora = LocalDateTime.parse(fecha)
            binding.fechaEditableTextPackagetourist.text = "El servicio se efectuará el "+ fechaHora.dayOfMonth + "/" + fechaHora.monthValue  + "/" + fechaHora.year
        }
        else{
            binding.fechaEditableTextPackagetourist.text = fecha
        }
        if(binding.fechaEditableTextPackagetourist.text != ""){
            binding.fechaEditableTextPackagetourist.visibility = View.VISIBLE
        }
        urlImagen = intent.getSerializableExtra("urlImagen") as String?
        if(urlImagen != null && !urlImagen!!.isEmpty()){
            Picasso.get().load(urlImagen).placeholder(R.drawable.paquete_general).error(R.drawable.paquete_general).into(binding.imageViewPackageType)
        }
        val costo = intent.getSerializableExtra("costo") as Float
        binding.costoEditableTextPackagetourist.setText(costo.toString())
        val servicios = intent.getSerializableExtra("servicios") as MutableList<Servicio>?
        if(servicios != null){
            this.servicios = servicios
        }
        serviciosInterAdapter = ServiciosInterAdapter(this, this.servicios)
        binding.serviciosInterListView.adapter = serviciosInterAdapter
        editarPaquetePresenter = EditarPaquetePresenter(this, binding.fechaEditableTextPackagetourist)
        day = intent.getSerializableExtra("dia") as Int
        month = intent.getSerializableExtra("mes") as Int
        year = intent.getSerializableExtra("year") as Int
    }


    fun onSubirImagen(){
        binding.subirImagenBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            val inputStream = contentResolver.openInputStream(selectedImage!!)
            bitmapPortada = BitmapFactory.decodeStream(inputStream)
            binding.imageViewPackageType.setImageBitmap(bitmapPortada)
        }
    }

    fun eventoSeleccionarFecha(){
        binding.selecccionarFecha.setOnClickListener{
            editarPaquetePresenter.pedirFecha()
            fechaPuesta = true
        }
    }

    fun eventoAgregarServicio(){
        binding.agregarServicioBtn.setOnClickListener{
            servicios = serviciosInterAdapter.data
            val intent = Intent(this, AgregarServicios::class.java)
            val nombrePaquete = binding.nombrePaqueteEtxt.text
            val fecha = binding.fechaEditableTextPackagetourist.text
            val costoPaquete = binding.costoEditableTextPackagetourist.text.toString()
            var valorPasado = 0.0f
            if(!costoPaquete.isEmpty()){
                valorPasado = costoPaquete.toFloat()
            }
            val bitmap = binding.imageViewPackageType.drawable.toBitmap()
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            intent.putExtra("usuario", usuario)
            intent.putExtra("nombrePaquete", nombrePaquete.toString())
            intent.putExtra("fecha", fecha.toString())
            intent.putExtra("costo", valorPasado)
            intent.putExtra("servicios", ArrayList(servicios))
            var dia = day
            var mes = month
            var year = year
            if(fechaPuesta){
                dia = editarPaquetePresenter.day
                mes = editarPaquetePresenter.month
                year = editarPaquetePresenter.year
            }
            intent.putExtra("dia", dia)
            intent.putExtra("mes", mes)
            intent.putExtra("year", year)
            intent.putExtra("editar", true)
            intent.putExtra("urlImagen", urlImagen)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eventoActualizarPaquete(){
        binding.aceptarCambiosBtn.setOnClickListener{
            if(servicios.size == 0){
                Toast.makeText(this@EditarPaqueteActivity, "Necesitas al menos un servicio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            servicios = serviciosInterAdapter.data
            if(fechaPuesta) {
                this.day = editarPaquetePresenter.day
                this.month = editarPaquetePresenter.month
                this.year = editarPaquetePresenter.year
            }
            if(binding.nombrePaqueteEtxt.text.equals("") || binding.organizadorTextEditablePackageT.text.equals("") || binding.fechaEditableTextPackagetourist.visibility == View.GONE ||
                binding.costoEditableTextPackagetourist.text.equals("") || binding.fechaEditableTextPackagetourist.visibility == View.GONE || binding.imageViewPackageType.drawable.toString().toIntOrNull() != null){
                Toast.makeText(this@EditarPaqueteActivity, "Completa todos los datos antes de registrar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val random = Random(12345L)
            val numeroAleatorio = random.nextInt(24)
            val numeroAleatorio2 = random.nextInt(60)
            subirImagen(binding.nombrePaqueteEtxt.text.toString(), LocalDateTime.of(year, month, day, numeroAleatorio, numeroAleatorio2), object:
                OnSubirImagen {
                override fun onSubirImagen(url: String?) {
                    if(url == null){
                        Toast.makeText(this@EditarPaqueteActivity, "no se pudo subir la imagen", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val numeroAleatorio3 = random.nextInt(24)
                    val numeroAleatorio4 = random.nextInt(60)
                    val paquete = PaqueteTuristico(id, binding.nombrePaqueteEtxt.text.toString(), LocalDateTime.of(year, month, day, numeroAleatorio3, numeroAleatorio4).toString(),
                        binding.costoEditableTextPackagetourist.text.toString().toFloat(), url, usuario.correo, servicios)
                    actualizarPaquete(paquete)
                }
            })
        }
    }

    fun actualizarPaquete(paquete: PaqueteTuristico){
        editarPaquetePresenter.updatePaquete(paquete, this@EditarPaqueteActivity, object:
            OnUpdatePaquete {
            override fun onUpdatePaquete(realizado: Boolean) {
                if(realizado){
                    Toast.makeText(this@EditarPaqueteActivity, "se ha actualizado el paquete!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditarPaqueteActivity, DashboardInter::class.java)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                }
                Toast.makeText(this@EditarPaqueteActivity, "No se pudo crear el paquete", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun eventBack(){
        binding.bottomNavigationViewTourist.menu.findItem(R.id.menuMap).setOnMenuItemClickListener( object: OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem): Boolean {
                var intent = Intent(this@EditarPaqueteActivity, DashboardInter::class.java)
                startActivity(intent)
                return true
            }
        })
    }

    fun seguirPaquete(){
        binding.bottomNavigationViewTourist.menu.findItem(R.id.menuMap).setOnMenuItemClickListener( object: OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem): Boolean {
                var intent = Intent(this@EditarPaqueteActivity, FollowTouristActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
                return true
            }
        })
    }

    fun eventoChat(){
        binding.bottomNavigationViewTourist.menu.findItem(R.id.menuChat).setOnMenuItemClickListener( object: OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem): Boolean {
                var intent = Intent(this@EditarPaqueteActivity, ChatActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
                return true
            }
        })
    }

    fun subirImagen(nombrePaquete: String, fechaPaquete: LocalDateTime, onSubirImagen: OnSubirImagen) {
        val bitmapPortada = (binding.imageViewPackageType.drawable as BitmapDrawable).bitmap
        val carpetaDestino = firebaseStorage.reference.child(usuario.correo)
        val referenciaImagen = carpetaDestino.child(nombrePaquete +fechaPaquete.toString() + ".jpg")
        // Comprimir el bitmap a un stream de bytes
        val outputStream = ByteArrayOutputStream()
        bitmapPortada.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val bytes: ByteArray = outputStream.toByteArray()
        // Subir la imagen a Firebase Storage
        referenciaImagen.putBytes(bytes)
            .addOnSuccessListener { taskSnapshot ->
                // La imagen se subió exitosamente
                // Puedes obtener la URL de descarga de la imagen
                referenciaImagen.downloadUrl.addOnSuccessListener { uri ->
                    val url = uri.toString()
                    onSubirImagen.onSubirImagen(url)
                }
            }
            .addOnFailureListener { exception ->
                onSubirImagen.onSubirImagen(null)
            }
    }
}