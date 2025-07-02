package com.example.alquilervehiculos.ui.vehiculos.edit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.alquilervehiculos.databinding.ActivityVehiculoEditBinding
import com.example.alquilervehiculos.data.model.Vehiculo
import com.example.alquilervehiculos.viewmodel.VehiculoViewModel

class VehiculoEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehiculoEditBinding
    private val viewModel: VehiculoViewModel by viewModels()
    private var vehiculoId: Int? = null // null significa nuevo vehículo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehiculoEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vehiculoId = intent.getIntExtra("vehiculoId", -1).takeIf { it != -1 }

        if (vehiculoId != null) {
            viewModel.getVehiculo(vehiculoId!!).observe(this) { vehiculo ->
                vehiculo?.let { fillForm(it) }
            }
        }

        binding.btnSave.setOnClickListener {
            val vehiculo = collectFormData()
            if (vehiculo != null) {
                if (vehiculoId == null) {
                    viewModel.addVehiculo(vehiculo)
                } else {
                    vehiculoId?.let { id ->
                        viewModel.updateVehiculo(id, vehiculo)
                    }
                }

                Toast.makeText(this, "Vehículo guardado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fillForm(vehiculo: Vehiculo) {
        binding.etMarca.setText(vehiculo.marca)
        binding.etModelo.setText(vehiculo.modelo)
        binding.etAnio.setText(vehiculo.anio.toString())
        binding.etPlaca.setText(vehiculo.placa)
        binding.etColor.setText(vehiculo.color)
        binding.etTipo.setText(vehiculo.tipo)
        binding.etPrecioPorDia.setText(vehiculo.precioPorDia.toString())
        binding.etEstado.setText(vehiculo.estado)
    }

    private fun collectFormData(): Vehiculo? {
        val marca = binding.etMarca.text.toString().trim()
        val modelo = binding.etModelo.text.toString().trim()
        val anioStr = binding.etAnio.text.toString().trim()
        val placa = binding.etPlaca.text.toString().trim()
        val color = binding.etColor.text.toString().trim()
        val tipo = binding.etTipo.text.toString().trim()
        val precioPorDiaStr = binding.etPrecioPorDia.text.toString().trim()
        val estado = binding.etEstado.text.toString().trim()

        if (marca.isEmpty() || modelo.isEmpty() || anioStr.isEmpty() || placa.isEmpty()
            || color.isEmpty() || tipo.isEmpty() || precioPorDiaStr.isEmpty() || estado.isEmpty()) {
            return null
        }

        val anio = anioStr.toIntOrNull() ?: return null
        val precioPorDia = precioPorDiaStr.toDoubleOrNull() ?: return null

        return Vehiculo(
            vehiculoId = vehiculoId ?: 0, // 0 o el id existente
            marca = marca,
            modelo = modelo,
            anio = anio,
            placa = placa,
            color = color,
            tipo = tipo,
            precioPorDia = precioPorDia,
            estado = estado
        )
    }
}
