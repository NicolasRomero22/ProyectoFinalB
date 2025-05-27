package com.example.proyectofinal.ViewModel

import androidx.lifecycle.*
import com.example.proyectofinal.model.Categoria
import com.example.proyectofinal.Repository.CategoriaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriaViewModel : ViewModel() {

    private val repository = CategoriaRepository()
    private val _categorias = MutableLiveData<List<Categoria>>(emptyList())
    val categorias: LiveData<List<Categoria>> = _categorias

    fun getCategorias() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.getCategorias()
            }
            _categorias.postValue(lista)
        }
    }

    fun getCategoria(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getCategoriaById(id)
            }
        }
    }

    fun guardarCategoria(categoria: Categoria) {
        viewModelScope.launch {
            repository.crearCategoria(categoria)
            getCategorias()
        }
    }

    fun eliminarCategoria(id: Long) {
        viewModelScope.launch {
            repository.eliminarCategoria(id)
            getCategorias()
        }
    }
}
