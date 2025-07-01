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
    val registroSuccess = MutableLiveData<Boolean>() // ✅ NUEVO

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RestClient.api.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    loginResult.postValue(response.body())
                } else {
                    loginResult.postValue(null)
                }
            } catch (e: Exception) {
                loginResult.postValue(null)
            }
        }
    }


    fun register(nombreCompleto: String, email: String, password: String, dni: String) {
        val request = RegistroRequest(
            nombreCompleto = nombreCompleto,
            email = email,
            password = password,
            dni = dni
        )

    }
}
