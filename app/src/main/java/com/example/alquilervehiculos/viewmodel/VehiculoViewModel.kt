package com.example.alquilervehiculos.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilervehiculos.data.model.Vehiculo
import com.example.alquilervehiculos.data.model.AlquilerRequest
import com.example.alquilervehiculos.data.model.AlquilerResponse
import com.example.alquilervehiculos.network.RestClient
import com.example.alquilervehiculos.repository.AlquilerRepository
import kotlinx.coroutines.launch

class VehiculoViewModel : ViewModel() {

    val vehiculos = MutableLiveData<List<Vehiculo>>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    // 🆕 Estas 3 líneas son las que te faltaban:
    private val repo = AlquilerRepository(RestClient.api)
    val alquilerResultado = MutableLiveData<AlquilerResponse?>()
    val alquilerError = MutableLiveData<String?>()

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

    fun crearAlquiler(dto: AlquilerRequest, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repo.crearAlquiler(dto)
                alquilerResultado.postValue(response)
                callback(true, "Alquiler creado (#${response.alquilerId})")
            } catch (e: Exception) {
                alquilerError.postValue(e.message)
                callback(false, e.message ?: "Error desconocido")
            }
        }
    }
}