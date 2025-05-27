package com.example.proyectofinal.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.proyectofinal.model.Prestamo
import com.example.proyectofinal.Repository.PrestamoRepository
import com.example.proyectofinal.model.PrestamoRequest
import com.example.proyectofinal.model.PrestamoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrestamoViewModel : ViewModel() {

    private val repository = PrestamoRepository()
    private val _prestamos = MutableLiveData<List<PrestamoResponse>>(emptyList())
    val prestamos: LiveData<List<PrestamoResponse>> = _prestamos

    fun getPrestamos() {
        viewModelScope.launch {
            val lista = repository.getPrestamos() // que devuelva List<PrestamoResponse>
            _prestamos.postValue(lista)
        }
    }

    fun getPrestamo(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getPrestamoById(id)
            }
        }
    }

    fun guardarPrestamo(prestamo: PrestamoRequest) {
        viewModelScope.launch {
            repository.crearPrestamo(prestamo)
            getPrestamos()
        }
    }

    fun eliminarPrestamo(id: Long) {
        viewModelScope.launch {
            repository.eliminarPrestamo(id)
            getPrestamos()
        }
    }
}