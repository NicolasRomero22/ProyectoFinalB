package com.example.proyectofinal.model

data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val email: String,
    val prestamos: List<Prestamo>? = null
)
