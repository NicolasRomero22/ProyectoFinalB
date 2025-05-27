package com.example.proyectofinal.interfaces

import com.example.proyectofinal.model.Prestamo
import com.example.proyectofinal.model.PrestamoRequest
import com.example.proyectofinal.model.PrestamoResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import okhttp3.OkHttpClient

interface PrestamoApi {
    @GET("api/prestamo/listar")
    suspend fun getPrestamos(): List<PrestamoResponse>

    @GET("api/prestamo/listar/{id}")
    suspend fun getPrestamoById(@Path("id") id: Long): Prestamo

    @POST("api/prestamo/guardar")
    suspend fun crearPrestamo(@Body prestamo: PrestamoRequest): PrestamoResponse

    @PUT("api/prestamo/actualizar/{id}")
    suspend fun actualizarPrestamo(@Path("id") id: Long, @Body prestamo: Prestamo): Prestamo

    @DELETE("api/prestamo/eliminar/{id}")
    suspend fun eliminarPrestamo(@Path("id") id: Long)
}

object PrestamoRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val prestamoApi: PrestamoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrestamoApi::class.java)
    }
}
