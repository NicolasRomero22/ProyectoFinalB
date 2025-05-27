package com.example.proyectofinal.model

data class PrestamoResponse(
    val id: Long?,
    val fechaPrestamo: String?,
    val fechaDevolucion: String?,
    val usuarioId: Long?,
    val nombreUsuario: String?,
    val ejemplarId: Long?,
    val codigoEjemplar: String?
)