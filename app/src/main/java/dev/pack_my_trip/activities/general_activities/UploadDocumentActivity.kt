package dev.pack_my_trip.activities.tourist_activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.R

class UploadDocumentActivity : AppCompatActivity() {

    private lateinit var buttonSelectFile: Button
    private lateinit var buttonUploadFile: Button
    private var fileUri: Uri? = null

    companion object {
        const val PICK_FILE_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_docs_upload)

        buttonSelectFile = findViewById(R.id.buttonSelectFile)
        buttonUploadFile = findViewById(R.id.buttonUploadFile)

        buttonSelectFile.setOnClickListener {
            showFileChooser()
        }

        buttonUploadFile.setOnClickListener {
            fileUri?.let {
                uploadFileToServer(it)
            } ?: run {
                Toast.makeText(this, "Seleccione un archivo primero.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "application/pdf"))
        }
        startActivityForResult(Intent.createChooser(intent, "Seleccione un archivo"), PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            fileUri = data.data
            Toast.makeText(this, "Archivo seleccionado: ${fileUri?.path}", Toast.LENGTH_LONG).show()
            buttonUploadFile.isEnabled = true
        }
    }

    private fun uploadFileToServer(fileUri: Uri) {
        val inputStream = contentResolver.openInputStream(fileUri)

        // Aquí implementarías la lógica para subir el archivo usando tu cliente HTTP favorito
        // Ejemplo con Retrofit o cualquier otra librería de red

        Toast.makeText(this, "Implementa la subida de archivo aquí.", Toast.LENGTH_LONG).show()
    }
}
