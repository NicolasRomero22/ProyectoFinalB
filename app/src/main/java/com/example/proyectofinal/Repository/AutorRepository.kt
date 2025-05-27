package com.example.proyectofinal.Repository

import com.example.proyectofinal.interfaces.AutorRetrofitClient
import com.example.proyectofinal.model.Autor

class AutorRepository {

    suspend fun getAutores(): List<Autor> {
        return AutorRetrofitClient.autorApi.getAutores()
    }

    suspend fun getAutorById(id: Long): Autor {
        return AutorRetrofitClient.autorApi.getAutorById(id)
    }

    suspend fun crearAutor(autor: Autor): Autor {
        return AutorRetrofitClient.autorApi.crearAutor(autor)
    }

    suspend fun eliminarAutor(id: Long) {
        AutorRetrofitClient.autorApi.eliminarAutor(id)
    }
}
