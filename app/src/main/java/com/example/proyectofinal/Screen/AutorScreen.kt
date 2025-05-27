package com.example.proyectofinal.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.ViewModel.AutorViewModel
import com.example.proyectofinal.model.Autor
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
@Composable
fun AutorScreen(viewModel: AutorViewModel = viewModel()) {
    val autores by viewModel.autores.observeAsState(emptyList())
    var nombre by remember { mutableStateOf("") }
    var autorAEliminar by remember { mutableStateOf<Autor?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var codigoBusqueda by remember { mutableStateOf("")}
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    LaunchedEffect(true) {
        viewModel.getAutores()
    }
    val librosFiltrados = autores.filter {
        it.id?.toString()?.contains(codigoBusqueda, ignoreCase = true) ?: false
    }
    Column(Modifier.padding(16.dp)) {
        Text(
            "Autor",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        // Campo para buscar por código
        TextField(
            value = codigoBusqueda,
            onValueChange = { codigoBusqueda = it },
            label = { Text("Buscar por código") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Codigo Icon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        Button(
            onClick = {
                scope.launch {
                    viewModel.guardarAutor(Autor(nombre = nombre))
                    nombre = ""
                    Toast.makeText(context, "Autor guardado", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar Autor")
        }
        if (librosFiltrados.isEmpty()) {
            Text("No hay libros disponibles")
        } else {
        LazyColumn {
            items(autores) { autor ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(Modifier.padding(8.dp)) {
                        Text("ID: ${autor.id}")
                        Text("Nombre: ${autor.nombre}")
                        Button(onClick = {
                            autorAEliminar = autor
                            showDialog = true
                        }) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    autorAEliminar?.id?.let { viewModel.eliminarAutor(it) }
                    showDialog = false
                }) { Text("Sí") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("No") } },
            title = { Text("Eliminar") },
            text = { Text("¿Desea eliminar este autor?") }
        )
    }
}