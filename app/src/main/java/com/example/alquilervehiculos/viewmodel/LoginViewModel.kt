package com.example.alquilervehiculos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilervehiculos.data.model.LoginRequest
import com.example.alquilervehiculos.data.model.LoginResponse

import com.example.alquilervehiculos.data.model.RegistroRequest
import com.example.alquilervehiculos.network.RestClient
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    val loginResult = MutableLiveData<LoginResponse?>()
    val registroSuccess = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {
        val request = LoginRequest(email.trim(), password)

        viewModelScope.launch {
            try {
                val response = RestClient.api.login(request)

                if (response.isSuccessful) {
                    loginResult.postValue(response.body())
                } else {
                    loginResult.postValue(null)
                    // Para depurar errores HTTP específicos
                    println("Error: Código ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                loginResult.postValue(null)
                println("Excepción: ${e.message}")
            }
        }
    }

    fun register(nombreCompleto: String, email: String, password: String, dni: String) {
        val request = RegistroRequest(
            nombreCompleto = nombreCompleto.trim(),
            email = email.trim(),
            password = password,
            dni = dni.trim()
        )

        viewModelScope.launch {
            try {
                val response = RestClient.api.register(request)
                registroSuccess.postValue(response.isSuccessful)
                if (!response.isSuccessful) {
                    println("Registro fallido: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                registroSuccess.postValue(false)
                println("Excepción en registro: ${e.message}")
            }
        }
    }
}
