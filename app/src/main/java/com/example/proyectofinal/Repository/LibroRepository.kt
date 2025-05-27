package com.example.proyectofinal.Repository

import com.example.proyectofinal.interfaces.RetrofitClient
import com.example.proyectofinal.model.Libro
import com.example.proyectofinal.model.LibroRequest
import retrofit2.Response

class LibroRepository {

    suspend fun getLibros(): List<Libro> {
        return RetrofitClient.libroApi.getLibros()
    }

    suspend fun getLibroById(id: Long): Libro {
        return RetrofitClient.libroApi.getLibroById(id)
    }

    suspend fun crearLibro(libro: LibroRequest): Response<Libro> {
        return RetrofitClient.libroApi.crearLibro(libro)
    }

    suspend fun eliminarLibro(id: Long){
        RetrofitClient.libroApi.eliminarLibro(id)
    }
}