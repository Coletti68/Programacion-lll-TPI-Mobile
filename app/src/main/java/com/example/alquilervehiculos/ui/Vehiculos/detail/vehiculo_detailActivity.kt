package com.example.alquilervehiculos.ui.Vehiculos.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.alquilervehiculos.databinding.ActivityVehiculoDetailBinding
import com.example.alquilervehiculos.viewmodel.VehiculoViewModel

class VehiculoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehiculoDetailBinding
    private val viewModel: VehiculoViewModel by viewModels()
    private var vehiculoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehiculoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vehiculoId = intent.getIntExtra("vehiculoId", 0)

        viewModel.getVehiculo(vehiculoId).observe(this) { vehiculo ->
            vehiculo?.let {
                binding.tvMarca.text = it.marca
                binding.tvModelo.text = it.modelo
                binding.tvAnio.text = it.anio.toString()
                binding.tvPlaca.text = it.placa
                binding.tvColor.text = it.color
                binding.tvTipo.text = it.tipo
                binding.tvPrecioPorDia.text = "$${it.precioPorDia}"
                binding.tvEstado.text = it.estado
            }
        }
    }
}
