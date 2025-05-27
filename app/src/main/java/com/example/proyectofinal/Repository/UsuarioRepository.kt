package com.example.proyectofinal.Repository

import com.example.proyectofinal.interfaces.UsuarioRetrofitClient
import com.example.proyectofinal.model.Usuario

class UsuarioRepository {

    suspend fun getUsuarios(): List<Usuario> {
        return UsuarioRetrofitClient.usuarioApi.getUsuarios()
    }

    suspend fun getUsuarioById(id: Long): Usuario {
        return UsuarioRetrofitClient.usuarioApi.getUsuarioById(id)
    }

    suspend fun crearUsuario(usuario: Usuario): Usuario {
        return UsuarioRetrofitClient.usuarioApi.crearUsuario(usuario)
    }

    suspend fun eliminarUsuario(id: Long) {
        UsuarioRetrofitClient.usuarioApi.eliminarUsuario(id)
    }
}
