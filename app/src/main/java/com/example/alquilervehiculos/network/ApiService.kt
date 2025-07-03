package com.example.alquilervehiculos.network

import com.example.alquilervehiculos.data.model.LoginRequest
import com.example.alquilervehiculos.data.model.LoginResponse
import com.example.alquilervehiculos.data.model.RegistroRequest
import com.example.alquilervehiculos.data.model.Vehiculo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("Auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("Usuario")
    suspend fun registrarUsuario(@Body registro: RegistroRequest): Response<Unit>

    @GET("api/vehiculos")
    suspend fun obtenerVehiculos(): Response<List<Vehiculo>>

}