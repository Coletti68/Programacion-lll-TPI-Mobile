package com.example.alquilervehiculos.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://TU_API_BASE_URL/") // ← Usa la URL BASE de tu API (ej. http://192.168.0.100:5000/)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}