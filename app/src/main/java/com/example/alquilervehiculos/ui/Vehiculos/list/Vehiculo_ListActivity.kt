package com.example.alquilervehiculos.ui.vehiculos.list

import VehiculoAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alquilervehiculos.databinding.ActivityVehiculoList2Binding
import com.example.alquilervehiculos.ui.Vehiculos.detail.VehiculoDetailActivity
import com.example.alquilervehiculos.ui.vehiculos.edit.VehiculoEditActivity
import com.example.alquilervehiculos.viewmodel.VehiculoViewModel

class VehiculoListActivity : AppCompatActivity(), VehiculoAdapter.OnItemClickListener {

    private lateinit var binding: ActivityVehiculoList2Binding
    private val viewModel: VehiculoViewModel by viewModels()
    private lateinit var adapter: VehiculoAdapter  // Corrige el nombre aquí

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehiculoList2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = VehiculoAdapter(this)
        binding.recyclerViewVehiculos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewVehiculos.adapter = adapter

        viewModel.vehiculos.observe(this) { listaVehiculos ->
            adapter.submitList(listaVehiculos)
        }

        viewModel.fetchVehiculos() // Llama a tu función correcta para cargar vehículos

        binding.fabAddVehiculo.setOnClickListener {
            val intent = Intent(this, VehiculoEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(vehiculoId: Int) {
        val intent = Intent(this, VehiculoDetailActivity::class.java)
        intent.putExtra("vehiculoId", vehiculoId)
        startActivity(intent)
    }

    override fun onItemEditClick(vehiculoId: Int) {
        val intent = Intent(this, VehiculoEditActivity::class.java)
        intent.putExtra("vehiculoId", vehiculoId)
        startActivity(intent)
    }
}
