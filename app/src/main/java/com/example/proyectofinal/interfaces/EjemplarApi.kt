package com.example.proyectofinal.interfaces

import com.example.proyectofinal.model.Ejemplar
import com.example.proyectofinal.model.EjemplarRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface EjemplarApi {
    @GET("api/ejemplar/listar")
    suspend fun getEjemplares(): List<Ejemplar>

    @GET("api/ejemplar/listar/{id}")
    suspend fun getEjemplarById(@Path("id") id: Long): Ejemplar

    @POST("api/ejemplar/guardar")
    suspend fun crearEjemplar(@Body request: EjemplarRequest): Ejemplar

    @PUT("api/ejemplar/actualizar/{id}")
    suspend fun actualizarEjemplar(@Path("id") id: Long, @Body ejemplar: Ejemplar): Ejemplar

    @DELETE("api/ejemplar/eliminar/{id}")
    suspend fun eliminarEjemplar(@Path("id") id: Long)
}

object EjemplarRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val ejemplarApi: EjemplarApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EjemplarApi::class.java)
    }
}
