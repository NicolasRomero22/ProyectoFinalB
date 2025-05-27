package com.example.proyectofinal.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Prestamo(
    val id: Long? = null,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val usuario: Long? = null,     // Solo ID de usuario
    val ejemplar: Ejemplar? = null
)
