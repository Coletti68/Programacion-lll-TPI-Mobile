package com.example.alquilervehiculos.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilervehiculos.data.model.AlquilerRequest
import com.example.alquilervehiculos.data.model.Vehiculo
import com.example.alquilervehiculos.network.RestClient
import kotlinx.coroutines.launch

class VehiculoViewModel : ViewModel() {

    val vehiculos = MutableLiveData<List<Vehiculo>>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun cargarVehiculos() {
        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            try {
                val response = RestClient.api.obtenerVehiculos()
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    Log.d("VehiculoViewModel", "Vehículos recibidos: ${lista.size}")
                    vehiculos.postValue(lista)
                } else {
                    error.postValue("Error al cargar vehículos: ${response.code()}")
                }
            } catch (e: Exception) {
                error.postValue("Excepción: ${e.localizedMessage}")
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun crearAlquiler(request: AlquilerRequest, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RestClient.api.crearAlquiler(request)
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, "Error al crear alquiler: ${response.code()}")
                }
            } catch (e: Exception) {
                onResult(false, "Excepción: ${e.localizedMessage}")
            }
        }
    }
}

