package com.example.alquilervehiculos.data.model

data class RegistroRequest(
    val nombreCompleto: String,
    val email: String,
    val password: String,
    val dni: String
)
