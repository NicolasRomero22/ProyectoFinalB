package com.example.proyectofinal.Repository

import com.example.proyectofinal.interfaces.EjemplarRetrofitClient
import com.example.proyectofinal.model.Ejemplar
import com.example.proyectofinal.model.EjemplarRequest

class EjemplarRepository {

    suspend fun getEjemplares(): List<Ejemplar> {
        return EjemplarRetrofitClient.ejemplarApi.getEjemplares()
    }

    suspend fun getEjemplarById(id: Long): Ejemplar {
        return EjemplarRetrofitClient.ejemplarApi.getEjemplarById(id)
    }

    suspend fun crearEjemplar(request: EjemplarRequest): Ejemplar {
        return EjemplarRetrofitClient.ejemplarApi.crearEjemplar(request)
    }

    suspend fun eliminarEjemplar(id: Long) {
        EjemplarRetrofitClient.ejemplarApi.eliminarEjemplar(id)
    }
}
