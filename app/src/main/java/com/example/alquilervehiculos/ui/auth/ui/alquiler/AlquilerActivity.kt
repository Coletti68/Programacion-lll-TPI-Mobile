package com.example.alquilervehiculos.ui.auth.ui.alquiler

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.alquilervehiculos.R

class AlquilerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alquiler)

        val marca = intent.getStringExtra("marca") ?: "-"
        val modelo = intent.getStringExtra("modelo") ?: "-"
        val placa = intent.getStringExtra("placa") ?: "-"

        findViewById<TextView>(R.id.tvVehiculoInfo).text =
            "Vehículo seleccionado:\n$marca $modelo\nPlaca: $placa"
    }
}
