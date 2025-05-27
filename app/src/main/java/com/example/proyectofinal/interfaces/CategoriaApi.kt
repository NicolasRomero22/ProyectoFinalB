package com.example.proyectofinal.interfaces

import com.example.proyectofinal.model.Categoria
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CategoriaApi {
    @GET("api/categoria/listar")
    suspend fun getCategorias(): List<Categoria>

    @GET("api/categoria/listar/{id}")
    suspend fun getCategoriaById(@Path("id") id: Long): Categoria

    @POST("api/categoria/guardar")
    suspend fun crearCategoria(@Body categoria: Categoria): Categoria

    @PUT("api/categoria/actualizar/{id}")
    suspend fun actualizarCategoria(@Path("id") id: Long, @Body categoria: Categoria): Categoria

    @DELETE("api/categoria/eliminar/{id}")
    suspend fun eliminarCategoria(@Path("id") id: Long)
}

object CategoriaRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val categoriaApi: CategoriaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoriaApi::class.java)
    }
}
