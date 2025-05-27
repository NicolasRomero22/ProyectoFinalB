package com.example.proyectofinal.model

data class Ejemplar(
    val id: Long? = null,
    val codigo: String,
    val libroId: Long,
    val prestamos: List<Prestamo>? = null
)