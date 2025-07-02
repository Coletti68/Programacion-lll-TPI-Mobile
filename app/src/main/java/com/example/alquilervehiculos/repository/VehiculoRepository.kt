package com.example.alquilervehiculos.repository

import com.example.alquilervehiculos.data.model.Vehiculo
import com.example.alquilervehiculos.network.ApiService

class VehiculoRepository(private val apiService: ApiService) {

    suspend fun getVehiculos() = apiService.getVehiculos()

    suspend fun getVehiculoById(id: Int) = apiService.getVehiculoById(id)

    suspend fun addVehiculo(vehiculo: Vehiculo) = apiService.addVehiculo(vehiculo)

    suspend fun updateVehiculo(id: Int, vehiculo: Vehiculo) = apiService.updateVehiculo(id, vehiculo)

    suspend fun deleteVehiculo(id: Int) = apiService.deleteVehiculo(id)
}
