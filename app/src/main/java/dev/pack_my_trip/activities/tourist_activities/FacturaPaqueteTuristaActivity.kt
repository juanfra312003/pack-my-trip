package dev.pack_my_trip.activities.tourist_activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.pack_my_trip.R
import dev.pack_my_trip.activities.general_activities.ChatActivity
import dev.pack_my_trip.databinding.ActivityFacturaPaqueteTuristaBinding
import dev.pack_my_trip.models.models_tourist.PaquetesPorTurista
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FacturaPaqueteTuristaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFacturaPaqueteTuristaBinding
    private lateinit var paqueteTurista: PaquetesPorTurista

    // Elementos de Texto del PDF
    private var mapaTexto: HashMap<String, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacturaPaqueteTuristaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el paquete por turista a partir de la actividad anterior.
        paqueteTurista = intent.getSerializableExtra("paquete_turista") as? PaquetesPorTurista ?: return

        // Cargar los valores de los textos
        loadDataText()

        // Manejar botones de navegación
        manageButtons()

        generatePDF()
    }

    // Funciones para el manejo de los datos del PDF
    private fun loadDataText() {
        mapaTexto["titulo"] = paqueteTurista.paqueteActual.nombre ?: ""
        mapaTexto["usuario"] = paqueteTurista.uuid_user.toString()
        mapaTexto["organizador"] = paqueteTurista.paqueteActual.nombreOrganizador ?: ""
        mapaTexto["fecha"] = paqueteTurista.fecha.toString()
        mapaTexto["precio"] = paqueteTurista.paqueteActual.precio.toString()

        val servicios = StringBuilder("Servicios del Paquete:\n")
        paqueteTurista.paqueteActual.servicios.forEach { servicio ->
            servicios.append("$servicio\n")
        }
        mapaTexto["servicios"] = servicios.toString()
    }

    private fun generatePDF() {

        val downloadsDir = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Factura.pdf")
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }

        val pdfDocument = PdfDocument()

        val titulo = TextPaint()
        val usuarioTitulo = TextPaint()
        val usuarioValues = TextPaint()

        val organizadorTitulo = TextPaint()
        val organizadorValues = TextPaint()

        val fechaTitulo = TextPaint()
        val fechaValues = TextPaint()

        val precioTitulo = TextPaint()
        val precioValues = TextPaint()

        val serviciosTitulo = TextPaint()
        val serviciosValues = TextPaint()

        // Crear la página
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        titulo.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titulo.textSize = 28f
        canvas.drawText(mapaTexto["titulo"]!!, 40f, 50f, titulo)

        usuarioTitulo.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        usuarioTitulo.textSize = 15f
        canvas.drawText("Usuario: ", 40f, 100f, usuarioTitulo)

        usuarioValues.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        usuarioValues.textSize = 14f
        canvas.drawText("Usuario: " + mapaTexto["usuario"]!!, 40f, 100f, usuarioValues)

        organizadorTitulo.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        organizadorTitulo.textSize = 15f
        canvas.drawText("Organizador: ", 40f, 150f, organizadorTitulo)

        organizadorValues.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        organizadorValues.textSize = 14f
        canvas.drawText("Organizador: " + mapaTexto["organizador"]!!, 40f, 150f, organizadorValues)

        fechaTitulo.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        fechaTitulo.textSize = 15f
        canvas.drawText("Fecha: ", 40f, 200f, fechaTitulo)

        fechaValues.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        fechaValues.textSize = 14f
        canvas.drawText("Fecha: " + mapaTexto["fecha"]!!, 40f, 200f, fechaValues)

        precioTitulo.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        precioTitulo.textSize = 15f
        canvas.drawText("Precio: ", 40f, 250f, precioTitulo)

        precioValues.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        precioValues.textSize = 14f
        canvas.drawText("Precio: " + mapaTexto["precio"]!!, 40f, 250f, precioValues)

        serviciosTitulo.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        serviciosTitulo.textSize = 15f
        canvas.drawText("Servicios: ", 40f, 300f, serviciosTitulo)

        serviciosValues.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        serviciosValues.textSize = 14f
        canvas.drawText(mapaTexto["servicios"]!!, 40f, 300f, serviciosValues)


        pdfDocument.finishPage(page)

        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Factura.pdf")

        try {
            FileOutputStream(downloadsDir).use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            Log.d("FacturaPaqueteTurista", "PDF generado en: ${downloadsDir.absolutePath}")
            showPDF()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar el PDF", Toast.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
        }
    }

    private fun showPDF() {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Factura.pdf")
        binding.pdfViewFacturaTurista.fromFile(file)
        binding.pdfViewFacturaTurista.show()
    }

    // Funciones para los permisos de escritura y lectura en memoria
    private fun checkPermissions(): Boolean {
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return writePermission == PackageManager.PERMISSION_GRANTED && readPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                generatePDF()
            } else {
                Toast.makeText(this, "Permisos necesarios para generar el PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }

    // Funciones para el manejo de botones y navegabilidad
    private fun manageButtons() {
        manageNavBar()
        manageOtherButtons()
    }

    private fun manageOtherButtons() {
        binding.buttonBackFacturaTurista.setOnClickListener {
            val intent = Intent(this, DetailsPackageTourist::class.java)
            intent.putExtra("paquete_turista", paqueteTurista)
            startActivity(intent)
        }
    }

    private fun manageNavBar() {
        binding.bottomNavigationViewTourist.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuBack -> {
                    val intent = Intent(this, PackageTouristActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    startActivity(intent)
                    true
                }
                R.id.menuChat -> {
                    val intent = Intent(this, ChatActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    startActivity(intent)
                    true
                }
                R.id.menuMap -> {
                    val intent = Intent(this, TouristMapActivity::class.java)
                    intent.putExtra("paquete_turista", paqueteTurista)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}