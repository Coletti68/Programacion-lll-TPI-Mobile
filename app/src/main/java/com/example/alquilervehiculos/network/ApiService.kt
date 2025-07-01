package com.example.alquilervehiculos.network

import com.example.alquilervehiculos.data.model.LoginRequest
import com.example.alquilervehiculos.data.model.LoginResponse
import com.example.alquilervehiculos.data.model.RegistroRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/Auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body request: RegistroRequest): Response<Void>

}