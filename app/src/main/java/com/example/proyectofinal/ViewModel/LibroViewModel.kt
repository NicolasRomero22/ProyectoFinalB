package com.example.proyectofinal.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.proyectofinal.model.Libro
import com.example.proyectofinal.Repository.LibroRepository
import com.example.proyectofinal.interfaces.RetrofitClient
import com.example.proyectofinal.model.LibroRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibroViewModel : ViewModel() {

    private val repository = LibroRepository()
    private val _libros = MutableLiveData<List<Libro>>(emptyList())
    val libros: LiveData<List<Libro>> = _libros
    fun getLibros() {
        viewModelScope.launch {
            val librosList = withContext(Dispatchers.IO) {
                repository.getLibros()
            }
            _libros.postValue(librosList)
        }
    }
    var libroBuscado by mutableStateOf<Libro?>(null)
        private set
    fun getLibro(id: Long) {
        viewModelScope.launch {
            val libro = withContext(Dispatchers.IO) {
                repository.getLibroById(id)
            }
            libroBuscado = libro
        }
    }

    fun guardarLibro(
        titulo: String,
        categoriaId: Long?,
        autorId: Long?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val libroRequest = LibroRequest(
            titulo = titulo,
            categoriaId = categoriaId,
            autorId = autorId,
        )

        viewModelScope.launch {
            try {
                val response = RetrofitClient.libroApi.crearLibro(libroRequest)

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error al guardar: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.message}")
            }
        }
    }


    fun eliminarLibro(id: Long) {
        viewModelScope.launch {
            repository.eliminarLibro(id)
            getLibros()
        }
    }
}