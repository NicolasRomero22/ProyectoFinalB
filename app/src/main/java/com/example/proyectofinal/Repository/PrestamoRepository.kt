package com.example.proyectofinal.Repository

import com.example.proyectofinal.interfaces.PrestamoRetrofitClient
import com.example.proyectofinal.model.Prestamo
import com.example.proyectofinal.model.PrestamoRequest
import com.example.proyectofinal.model.PrestamoResponse

class PrestamoRepository {

    suspend fun getPrestamos(): List<PrestamoResponse> {
        return PrestamoRetrofitClient.prestamoApi.getPrestamos()
    }

    suspend fun getPrestamoById(id: Long): Prestamo {
        return PrestamoRetrofitClient.prestamoApi.getPrestamoById(id)
    }

    suspend fun crearPrestamo(prestamo: PrestamoRequest): PrestamoResponse {
        return PrestamoRetrofitClient.prestamoApi.crearPrestamo(prestamo)
    }

    suspend fun eliminarPrestamo(id: Long) {
        PrestamoRetrofitClient.prestamoApi.eliminarPrestamo(id)
    }
}
