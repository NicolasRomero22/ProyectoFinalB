package com.example.proyectofinal.model

data class Categoria(
    val id: Long? = null,
    val nombre: String,
    val libros: List<Libro>? = null
)