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
                val request = LoginRequest(email.trim(), password)
                println("馃摛 Enviando login: ${request.email} / ${request.password}")

                viewModelScope.launch {
                    try {
                        val response = RestClient.api.login(request)
                        println("馃摜 Respuesta: c贸digo ${response.code()} - body=${response.body()} - error=${response.errorBody()?.string()}")

                        if (response.isSuccessful) {
                            loginResult.postValue(response.body())
                        } else {
                            loginResult.postValue(null)
                        }
                    } catch (e: Exception) {
                        println("馃挜 Excepci贸n login: ${e.message}")
                        loginResult.postValue(null)
                    }
                }

                val response = RestClient.api.login(request)

                if (response.isSuccessful) {
                    loginResult.postValue(response.body())
                } else {
                    loginResult.postValue(null)
                    // Para depurar errores HTTP espec铆ficos
                    println("Error: Codigo ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                loginResult.postValue(null)
                println("Excepcion: ${e.message}")
            }
        }
    }
}