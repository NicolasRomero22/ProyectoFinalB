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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.ViewModel.CategoriaViewModel
import com.example.proyectofinal.model.Categoria
import kotlinx.coroutines.launch
@Composable
fun CategoriaScreen(viewModel: CategoriaViewModel = viewModel()) {
    val categorias by viewModel.categorias.observeAsState(emptyList())
    var nombre by remember { mutableStateOf("") }
    var categoriaAEliminar by remember { mutableStateOf<Categoria?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var codigoBusqueda by remember { mutableStateOf("")}
    LaunchedEffect(true) { viewModel.getCategorias() }
    val librosFiltrados = categorias.filter {
        it.id?.toString()?.contains(codigoBusqueda, ignoreCase = true) ?: false
    }

    Column(Modifier.padding(16.dp)) {
        Text(
            "Categoria",
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

        Button(onClick = {
            scope.launch {
                viewModel.guardarCategoria(Categoria(nombre = nombre))
                nombre = ""
                Toast.makeText(context, "Categoría guardada", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Agregar Categoría")
        }

        if (librosFiltrados.isEmpty()) {
            Text("No hay libros disponibles")
        } else {
            LazyColumn {
                items(categorias) { cat ->
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text("ID: ${cat.id}")
                            Text("Nombre: ${cat.nombre}")
                            Button(onClick = {
                                categoriaAEliminar = cat
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
                    categoriaAEliminar?.id?.let { viewModel.eliminarCategoria(it) }
                    showDialog = false
                }) { Text("Sí") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("No") } },
            title = { Text("Eliminar") },
            text = { Text("¿Eliminar categoría?") }
        )
    }
}