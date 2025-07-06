package com.example.alquilervehiculos.ui.auth.ui.alquiler

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.alquilervehiculos.R
import com.example.alquilervehiculos.data.model.AlquilerRequest
import com.example.alquilervehiculos.viewmodel.VehiculoViewModel

class AlquilerActivity : AppCompatActivity() {

    private val viewModel: VehiculoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alquiler)

        // 🧾 Recuperar datos del vehículo desde el intent
        val vehiculoId = intent.getIntExtra("vehiculoId", 0)
        val modelo = intent.getStringExtra("modelo") ?: "Modelo desconocido"
        val placa = intent.getStringExtra("placa") ?: "Sin patente"

        // 🔗 Referencias a la UI
        val tvInfo = findViewById<TextView>(R.id.tvVehiculoInfo)
        val etFechaInicio = findViewById<EditText>(R.id.etFechaInicio)
        val etFechaFin = findViewById<EditText>(R.id.etFechaFin)
        val chkTerminos = findViewById<CheckBox>(R.id.chkTerminos)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        // ℹ️ Mostrar información del vehículo
        tvInfo.text = "Vehículo seleccionado:\n$modelo\nPlaca: $placa"

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val usuarioId = prefs.getInt("usuarioId", 0)

        // 🚀 Acción al confirmar alquiler
        btnConfirmar.setOnClickListener {
            val fechaInicio = etFechaInicio.text.toString().trim()
            val fechaFin = etFechaFin.text.toString().trim()
            val acepto = chkTerminos.isChecked

            if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                Toast.makeText(this, "Completá ambas fechas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!acepto) {
                Toast.makeText(this, "Debés aceptar los términos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alquiler = AlquilerRequest(
                usuarioId = usuarioId,
                vehiculoId = vehiculoId,
                empleadoId = 1,
                fechaInicio = "${fechaInicio}T10:00:00",
                fechaFin = "${fechaFin}T10:00:00",
                estado = "Pendiente",
                aceptoTerminos = true
            )

            viewModel.crearAlquiler(alquiler) { exito, mensaje ->
                tvResultado.text = if (exito) "✔️ $mensaje" else "❌ $mensaje"
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                if (exito) finish() // cerrar y volver al home si querés
            }
        }
    }
}