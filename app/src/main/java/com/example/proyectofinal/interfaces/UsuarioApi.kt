package com.example.proyectofinal.interfaces

import com.example.proyectofinal.model.Usuario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UsuarioApi {
    @GET("api/usuario/listar")
    suspend fun getUsuarios(): List<Usuario>

    @GET("api/usuario/listar/{id}")
    suspend fun getUsuarioById(@Path("id") id: Long): Usuario

    @POST("api/usuario/guardar")
    suspend fun crearUsuario(@Body usuario: Usuario): Usuario

    @PUT("api/usuario/actualizar/{id}")
    suspend fun actualizarUsuario(@Path("id") id: Long, @Body usuario: Usuario): Usuario

    @DELETE("api/usuario/eliminar/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Long)
}

object UsuarioRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val usuarioApi: UsuarioApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioApi::class.java)
    }
}
