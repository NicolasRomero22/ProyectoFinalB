package com.example.proyectofinal.Screen
import android.widget.Toast
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.*
import com.example.proyectofinal.model.*
import com.example.proyectofinal.ViewModel.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import com.example.proyectofinal.DropMenus.DropdownMenuAutor
import com.example.proyectofinal.DropMenus.DropdownMenuCategoria
import kotlin.text.toLongOrNull
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
@Preview(showBackground = true)
@Composable
fun LibroScreen(
    libroViewModel: LibroViewModel = viewModel(),
    categoriaViewModel: CategoriaViewModel = viewModel(),
    autorViewModel: AutorViewModel = viewModel(),
    ejemplarViewModel: EjemplarViewModel = viewModel()
) {
    val ejemplares by ejemplarViewModel.ejemplares.observeAsState(emptyList())
    val libros by libroViewModel.libros.observeAsState(emptyList())
    val categorias by categoriaViewModel.categorias.observeAsState(emptyList())
    val autores by autorViewModel.autores.observeAsState(emptyList())


    var titulo by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<Categoria?>(null) }
    var autorSeleccionado by remember { mutableStateOf<Autor?>(null) }
    var ejemplarSeleccionado by remember { mutableStateOf<Ejemplar?>(null) }
    var codigoBusqueda by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var libroAEliminar by remember { mutableStateOf<Libro?>(null) }
    var idBusqueda by remember { mutableStateOf("") }
    val libroBuscado = libroViewModel.libroBuscado
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(true) {
        libroViewModel.getLibros()
        categoriaViewModel.getCategorias()
        autorViewModel.getAutores()
        ejemplarViewModel.getEjemplares() // <-- Cargar ejemplares
    }

    val librosFiltrados = libros.filter {
        it.id?.toString()?.contains(codigoBusqueda, ignoreCase = true) ?: false
    }
    val librosFiltradosConNombres = librosFiltrados.map { libro ->
        val categoriaNombre = categorias.find { it.id == libro.categoria?.toLongOrNull() }?.nombre ?: "Sin categoría"
        val autorNombre = autores.find { it.id == libro.autor?.toLongOrNull() }?.nombre ?: "Sin autor"
        libro.copy(
            categoria = categoriaNombre,
            autor = autorNombre,
        )
    }

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
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            "Agregar Nuevo Libro",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        TextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        DropdownMenuCategoria(
            categorias = categorias,
            categoriaSeleccionada = categoriaSeleccionada,
            onCategoriaSelected = { categoriaSeleccionada = it }
        )

        DropdownMenuAutor(
            autores = autores,
            autorSeleccionado = autorSeleccionado,
            onAutorSelected = { autorSeleccionado = it }
        )


        Button(
            onClick = {
                if (titulo.isNotBlank() && categoriaSeleccionada != null && autorSeleccionado != null) {
                    scope.launch {
                        libroViewModel.guardarLibro(
                            titulo = titulo,
                            autorId = autorSeleccionado!!.id,
                            categoriaId = categoriaSeleccionada!!.id,
                            onSuccess = {
                                Toast.makeText(context, "Libro guardado con éxito", Toast.LENGTH_SHORT).show()
                                libroViewModel.getLibros()
                                ejemplarViewModel.getEjemplares() // <- por si se crean nuevos ejemplares
                            },
                            onError = { errorMsg ->
                                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                } else {
                    Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar Libro")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (librosFiltrados.isEmpty()) {
            Text("No hay libros disponibles")
        } else {
            LazyColumn {
                items(librosFiltradosConNombres) { libro ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Título: ${libro.titulo}")
                            Text("Categoría: ${libro.categoria?: "Sin categoría"}")
                            Text("Autor: ${libro.autor?: "Sin autor"}")
                            val ejemplaresDelLibro = ejemplares.filter { it.libroId == libro.id?.toLong() }
                            if (ejemplaresDelLibro.isEmpty()) {
                                Text("Ejemplares: Sin ejemplar")
                            } else {
                                Column {
                                    Text("Ejemplares:")
                                    ejemplaresDelLibro.forEach {
                                        Text("- Código: ${it.codigo}")
                                    }
                                }

                            Button(
                                onClick = {
                                    libroAEliminar = libro
                                    showDialog = true
                                },
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
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
            title = { Text("Confirmación") },
            text = { Text("¿Deseas eliminar este libro?") },
            confirmButton = {
                TextButton(onClick = {
                    libroAEliminar?.id?.let { id ->
                        libroViewModel.eliminarLibro(id)
                        libroViewModel.getLibros()
                    }
                    showDialog = false
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}
}

