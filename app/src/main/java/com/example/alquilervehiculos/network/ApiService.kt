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

    @POST("api/Auth/registro")
    suspend fun register(@Body request: RegistroRequest): Response<Void>

    /*@GET("api/Vehiculos")
   // suspend fun getVehiculos(): List<Vehiculo>

    //@GET("api/Vehiculos/{id}")
    suspend fun getVehiculoById(@Path("id") id: Int): Vehiculo

    @POST("api/Vehiculos")
    suspend fun addVehiculo(@Body vehiculo: Vehiculo): Vehiculo

    @PUT("api/Vehiculos/{id}")
    suspend fun updateVehiculo(@Path("id") id: Int, @Body vehiculo: Vehiculo): Vehiculo

    @DELETE("api/Vehiculos/{id}")
    suspend fun deleteVehiculo(@Path("id") id: Int)
    */


}