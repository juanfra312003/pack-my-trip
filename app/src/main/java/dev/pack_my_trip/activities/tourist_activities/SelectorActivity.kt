package dev.pack_my_trip.activities.tourist_activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.R

class SelectorActivity : AppCompatActivity() {

    private lateinit var spinnerRegion1: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_selector) // Asegúrate de que este es el nombre correcto del layout

        spinnerRegion1 = findViewById(R.id.spinnerRegion1)
        setUpSpinner(spinnerRegion1, R.array.region1_array)
    }

    private fun setUpSpinner(spinner: Spinner, arrayResourceId: Int) {
        val adapter = ArrayAdapter.createFromResource(
            this,
            arrayResourceId,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Listener para el Spinner
        spinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedRegion = parent?.getItemAtPosition(position) as String
                // Aquí puedes manejar la selección, por ejemplo, guardar la selección o cargar datos relacionados
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                // Opcionalmente maneja el caso en que no se selecciona nada
            }
        }
    }
}
