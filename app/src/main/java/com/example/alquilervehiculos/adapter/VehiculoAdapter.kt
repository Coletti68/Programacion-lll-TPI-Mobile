package com.example.alquilervehiculos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alquilervehiculos.R
import com.example.alquilervehiculos.data.model.Vehiculo

class VehiculoAdapter(
    private val vehiculos: List<Vehiculo>,
    private val onAlquilarClick: (Vehiculo) -> Unit
) : RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>() {

    inner class VehiculoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMarca: TextView = itemView.findViewById(R.id.tvMarca)
        val tvModelo: TextView = itemView.findViewById(R.id.tvModelo)
        val tvPlaca: TextView = itemView.findViewById(R.id.tvPlaca)
        val btnAlquilar: Button = itemView.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vehiculo_list_item, parent, false)
        return VehiculoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehiculoViewHolder, position: Int) {
        val vehiculo = vehiculos[position]
        holder.tvMarca.text = vehiculo.marca
        holder.tvModelo.text = vehiculo.modelo
        holder.tvPlaca.text = vehiculo.placa

        holder.btnAlquilar.setOnClickListener {
            onAlquilarClick(vehiculo)
        }
    }

    override fun getItemCount(): Int = vehiculos.size
}