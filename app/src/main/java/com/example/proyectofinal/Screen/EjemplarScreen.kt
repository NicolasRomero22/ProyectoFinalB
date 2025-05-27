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
import com.example.proyectofinal.DropMenus.DropdownMenuLibro
import com.example.proyectofinal.ViewModel.EjemplarViewModel
import com.example.proyectofinal.ViewModel.LibroViewModel
import com.example.proyectofinal.model.Ejemplar
import com.example.proyectofinal.model.Libro
import kotlinx.coroutines.launch
@Composable
fun EjemplarScreen(
    viewModel: EjemplarViewModel = viewModel(),
    libroViewModel: LibroViewModel = viewModel()
) {
    val ejemplares by viewModel.ejemplares.observeAsState(emptyList())
    val libros by libroViewModel.libros.observeAsState(emptyList())

    var codigo by remember { mutableStateOf("") }
    var libroSeleccionado by remember { mutableStateOf<Libro?>(null) }
    var ejemplarAEliminar by remember { mutableStateOf<Ejemplar?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var codigoBusqueda by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        viewModel.getEjemplares()
        libroViewModel.getLibros()
    }

    val librosFiltrados = ejemplares.filter {
        it.id?.toString()?.contains(codigoBusqueda, ignoreCase = true) ?: false
    }

    val esCodigoValido = codigo.matches(Regex("^[a-zA-Z0-9]{6}$"))

    Column(Modifier.padding(16.dp)) {
        Text(
            "Ejemplar",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

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

        TextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código (6 caracteres alfanuméricos)") },
            isError = !esCodigoValido && codigo.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )

        if (!esCodigoValido && codigo.isNotEmpty()) {
            Text(
                text = "El código debe tener exactamente 6 letras o números (sin símbolos)",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        DropdownMenuLibro(libros, libroSeleccionado) { libroSeleccionado = it }

        Button(
            onClick = {
                scope.launch {
                    if (!esCodigoValido) {
                        Toast.makeText(context, "El código debe tener exactamente 6 letras o números", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    val codigoExiste = ejemplares.any { it.codigo.equals(codigo.trim(), ignoreCase = true) }
                    if (codigoExiste) {
                        Toast.makeText(context, "Ya existe un ejemplar con ese código", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    if (libroSeleccionado != null && codigo.isNotBlank()) {
                        viewModel.guardarEjemplar(
                            codigo = codigo.trim(),
                            libroId = libroSeleccionado!!.id!!
                        )
                        viewModel.getEjemplares()
                        codigo = ""
                        Toast.makeText(context, "Ejemplar guardado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text("Agregar Ejemplar")
        }

        if (librosFiltrados.isEmpty()) {
            Text("No hay libros disponibles")
        } else {
            LazyColumn {
                items(ejemplares) { ej ->
                    val nombreLibro = libros.find { it.id == ej.libroId }?.titulo ?: "Desconocido"

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(Modifier.padding(8.dp)) {
                            Text("ID: ${ej.id}")
                            Text("Código: ${ej.codigo}")
                            Text("Libro: $nombreLibro")
                            Button(onClick = {
                                ejemplarAEliminar = ej
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
                    ejemplarAEliminar?.id?.let { viewModel.eliminarEjemplar(it) }
                    showDialog = false
                }) {
                    Text("Sí")
                }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("No") } },
            title = { Text("Eliminar") },
            text = { Text("¿Eliminar ejemplar?") }
        )
    }
}