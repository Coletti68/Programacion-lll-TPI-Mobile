package com.example.alquilervehiculos.viewmodel

import androidx.lifecycle.*
import com.example.alquilervehiculos.data.model.Vehiculo
import com.example.alquilervehiculos.repository.VehiculoRepository
import kotlinx.coroutines.launch

class VehiculoViewModel(private val repository: VehiculoRepository): ViewModel() {

    private val _vehiculos = MutableLiveData<List<Vehiculo>>()
    val vehiculos: LiveData<List<Vehiculo>> = _vehiculos

    private val _vehiculo = MutableLiveData<Vehiculo>()
    val vehiculo: LiveData<Vehiculo> = _vehiculo

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Obtener todos los vehículos
    fun fetchVehiculos() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val lista = repository.getVehiculos()
                _vehiculos.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al cargar vehículos: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // Obtener un solo vehículo por ID
    fun getVehiculo(id: Int): LiveData<Vehiculo> {
        viewModelScope.launch {
            _loading.value = true
            try {
                val v = repository.getVehiculoById(id)
                _vehiculo.value = v
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener el vehículo: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
        return vehiculo
    }

    // Agregar un nuevo vehículo
    fun addVehiculo(vehiculo: Vehiculo) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.addVehiculo(vehiculo)
                fetchVehiculos() // Actualizar lista después de agregar
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al agregar vehículo: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // (Opcional) eliminar vehículo
    fun deleteVehiculo(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.deleteVehiculo(id)
                fetchVehiculos()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al eliminar vehículo: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // (Opcional) editar vehículo
    fun updateVehiculo(id: Int, vehiculo: Vehiculo) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.updateVehiculo(id, vehiculo)
                fetchVehiculos()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al actualizar vehículo: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
