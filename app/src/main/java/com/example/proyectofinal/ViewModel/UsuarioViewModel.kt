package com.example.proyectofinal.ViewModel

import androidx.lifecycle.*
import com.example.proyectofinal.model.Usuario
import com.example.proyectofinal.Repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel : ViewModel() {

    private val repository = UsuarioRepository()
    private val _usuarios = MutableLiveData<List<Usuario>>(emptyList())
    val usuarios: LiveData<List<Usuario>> = _usuarios

    fun getUsuarios() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.getUsuarios()
            }
            _usuarios.postValue(lista)
        }
    }

    fun getUsuario(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getUsuarioById(id)
            }
        }
    }

    fun guardarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repository.crearUsuario(usuario)
            getUsuarios()
        }
    }

    fun eliminarUsuario(id: Long) {
        viewModelScope.launch {
            repository.eliminarUsuario(id)
            getUsuarios()
        }
    }
}
