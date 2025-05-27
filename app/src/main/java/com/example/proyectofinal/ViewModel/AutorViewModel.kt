package com.example.proyectofinal.ViewModel

import androidx.lifecycle.*
import com.example.proyectofinal.model.Autor
import com.example.proyectofinal.Repository.AutorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AutorViewModel : ViewModel() {

    private val repository = AutorRepository()
    private val _autores = MutableLiveData<List<Autor>>(emptyList())
    val autores: LiveData<List<Autor>> = _autores

    fun getAutores() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.getAutores()
            }
            _autores.postValue(lista)
        }
    }

    fun getAutor(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAutorById(id)
            }
        }
    }

    fun guardarAutor(autor: Autor) {
        viewModelScope.launch {
            repository.crearAutor(autor)
            getAutores()
        }
    }

    fun eliminarAutor(id: Long) {
        viewModelScope.launch {
            repository.eliminarAutor(id)
            getAutores()
        }
    }
}
