package com.example.proyectofinal.model

data class Autor(
    val id: Long? = null,
    val nombre: String,
    val libros: List<Libro>? = null
)