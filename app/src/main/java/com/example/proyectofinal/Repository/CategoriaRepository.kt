package com.example.proyectofinal.Repository

import com.example.proyectofinal.interfaces.CategoriaRetrofitClient
import com.example.proyectofinal.model.Categoria

class CategoriaRepository {

    suspend fun getCategorias(): List<Categoria> {
        return CategoriaRetrofitClient.categoriaApi.getCategorias()
    }

    suspend fun getCategoriaById(id: Long): Categoria {
        return CategoriaRetrofitClient.categoriaApi.getCategoriaById(id)
    }

    suspend fun crearCategoria(categoria: Categoria): Categoria {
        return CategoriaRetrofitClient.categoriaApi.crearCategoria(categoria)
    }

    suspend fun eliminarCategoria(id: Long) {
        CategoriaRetrofitClient.categoriaApi.eliminarCategoria(id)
    }
}
