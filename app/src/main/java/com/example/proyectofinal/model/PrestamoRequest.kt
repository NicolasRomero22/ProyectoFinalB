package com.example.proyectofinal.model

data class PrestamoRequest(
    val ejemplarId: Long,
    val usuarioId: Long,
    val fechaPrestamo: String,      // <- tipo String, no LocalDate
    val fechaDevolucion: String     // <- tipo String, no LocalDate
)