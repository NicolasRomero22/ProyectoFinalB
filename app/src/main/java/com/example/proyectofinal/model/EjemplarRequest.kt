package com.example.proyectofinal.model

data class EjemplarRequest(
    val codigo: String,
    val libroId: Long,
    val prestamos: List<Prestamo>? = null
)