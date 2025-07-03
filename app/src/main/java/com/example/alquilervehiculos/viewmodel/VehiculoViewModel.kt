package com.example.alquilervehiculos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    vehiculos.postValue(response.body())
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
}