package com.example.proyectofinal.interfaces

import com.example.proyectofinal.model.Autor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface AutorApi {
    @GET("api/autor/listar")
    suspend fun getAutores(): List<Autor>

    @GET("api/autor/listar/{id}")
    suspend fun getAutorById(@Path("id") id: Long): Autor

    @POST("api/autor/guardar")
    suspend fun crearAutor(@Body autor: Autor): Autor

    @PUT("api/autor/actualizar/{id}")
    suspend fun actualizarAutor(@Path("id") id: Long, @Body autor: Autor): Autor

    @DELETE("api/autor/eliminar/{id}")
    suspend fun eliminarAutor(@Path("id") id: Long)
}

object AutorRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val autorApi: AutorApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AutorApi::class.java)
    }
}