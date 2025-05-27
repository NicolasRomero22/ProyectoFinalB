package com.example.proyectofinal.ViewModel

import androidx.lifecycle.*
import com.example.proyectofinal.model.Ejemplar
import com.example.proyectofinal.Repository.EjemplarRepository
import com.example.proyectofinal.model.EjemplarRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EjemplarViewModel : ViewModel() {

    private val repository = EjemplarRepository()
    private val _ejemplares = MutableLiveData<List<Ejemplar>>(emptyList())
    val ejemplares: LiveData<List<Ejemplar>> = _ejemplares

    fun getEjemplares() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.getEjemplares()
            }
            _ejemplares.postValue(lista)
        }
    }

    fun getEjemplar(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getEjemplarById(id)
            }
        }
    }

    fun guardarEjemplar(codigo: String, libroId: Long) {
        viewModelScope.launch {
            val nuevo = repository.crearEjemplar(EjemplarRequest(codigo, libroId))
            val lista = _ejemplares.value.orEmpty().toMutableList()
            lista.add(nuevo)
            _ejemplares.postValue(lista)
        }
    }

    fun eliminarEjemplar(id: Long) {
        viewModelScope.launch {
            repository.eliminarEjemplar(id)
            getEjemplares()
        }
    }
}
