package com.example.proyectofinal.interfaces

import com.example.proyectofinal.model.Libro
import com.example.proyectofinal.model.LibroRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface LibroApi {
    @GET("api/libro/listar")
    suspend fun getLibros(): List<Libro>

    @GET("api/libro/listar/{id}")
    suspend fun getLibroById(@Path("id") id: Long): Libro

    @POST("api/libro/guardar")
    suspend fun crearLibro(@Body libro: LibroRequest): Response<Libro>


    @PUT("api/libro/actualizar/{id}")
    suspend fun actualizarLibro(@Path("id") id: Long, @Body libro: Libro): Libro

    @DELETE("api/libro/eliminar/{id}")
    suspend fun eliminarLibro(@Path("id") id: Long)
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val libroApi: LibroApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibroApi::class.java)
    }
}