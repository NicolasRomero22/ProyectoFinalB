package com.example.proyectofinal.Screen
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.example.proyectofinal.DropMenus.DropdownMenuEjemplar
import com.example.proyectofinal.DropMenus.DropdownMenuUsuario
import com.example.proyectofinal.ViewModel.EjemplarViewModel
import com.example.proyectofinal.ViewModel.PrestamoViewModel
import com.example.proyectofinal.ViewModel.UsuarioViewModel
import com.example.proyectofinal.model.Ejemplar
import com.example.proyectofinal.model.Prestamo
import com.example.proyectofinal.model.PrestamoRequest
import com.example.proyectofinal.model.PrestamoResponse
import com.example.proyectofinal.model.Usuario
import kotlinx.coroutines.launch
import java.time.LocalDate
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrestamoScreen(
    viewModel: PrestamoViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel(),
    ejemplarViewModel: EjemplarViewModel = viewModel()
) {
    val prestamos by viewModel.prestamos.observeAsState(emptyList<PrestamoResponse>())
    val usuarios by usuarioViewModel.usuarios.observeAsState(emptyList())
    val ejemplares by ejemplarViewModel.ejemplares.observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var fechaPrestamo by remember { mutableStateOf("") }
    var fechaDevolucion by remember { mutableStateOf("") }
    var prestamoAEliminar by remember { mutableStateOf<PrestamoResponse?>(null) }
    var usuarioSeleccionado by remember { mutableStateOf<Usuario?>(null) }
    var ejemplarSeleccionado by remember { mutableStateOf<Ejemplar?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var codigoBusqueda by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        viewModel.getPrestamos()
        usuarioViewModel.getUsuarios()
        ejemplarViewModel.getEjemplares()
    }

    // ✅ Ejemplares actualmente prestados
    val ejemplaresPrestados = prestamos.mapNotNull { it.ejemplarId }
    // ✅ Solo se muestran los que NO están prestados
    val ejemplaresDisponibles = ejemplares.filter { it.id !in ejemplaresPrestados }

    val prestamosFiltrados = prestamos.filter {
        it.id.toString().contains(codigoBusqueda, ignoreCase = true)
    }

    Column(Modifier.padding(16.dp)) {
        Text(
            "Préstamo",
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
            value = fechaPrestamo,
            onValueChange = { fechaPrestamo = it },
            label = { Text("Fecha Préstamo (yyyy-MM-dd)") }
        )
        TextField(
            value = fechaDevolucion,
            onValueChange = { fechaDevolucion = it },
            label = { Text("Fecha Devolución (yyyy-MM-dd)") }
        )

        DropdownMenuUsuario(usuarios, usuarioSeleccionado) { usuarioSeleccionado = it }

        // ✅ Dropdown solo con ejemplares disponibles
        DropdownMenuEjemplar(ejemplaresDisponibles, ejemplarSeleccionado) { ejemplarSeleccionado = it }

        if (ejemplaresDisponibles.isEmpty()) {
            Text("No hay ejemplares disponibles para préstamo", color = Color.Red)
        }

        Button(onClick = {
            if (usuarioSeleccionado != null && ejemplarSeleccionado != null) {
                try {
                    val fechaPrestamoLocalDate = LocalDate.parse(fechaPrestamo)
                    val fechaDevolucionLocalDate = LocalDate.parse(fechaDevolucion)

                    val prestamo = PrestamoRequest(
                        ejemplarId = ejemplarSeleccionado!!.id!!,
                        usuarioId = usuarioSeleccionado!!.id!!,
                        fechaPrestamo = fechaPrestamoLocalDate.toString(),
                        fechaDevolucion = fechaDevolucionLocalDate.toString()
                    )
                    scope.launch {
                        viewModel.guardarPrestamo(prestamo)
                        Toast.makeText(context, "Préstamo guardado", Toast.LENGTH_SHORT).show()
                        viewModel.getPrestamos()
                        ejemplarSeleccionado = null
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al parsear las fechas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Agregar Préstamo")
        }

        if (prestamosFiltrados.isEmpty()) {
            Text("No hay préstamos disponibles")
        } else {
            LazyColumn {
                items(prestamosFiltrados) { prestamo ->
                    Card(Modifier.fillMaxWidth().padding(8.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text("ID: ${prestamo.id}")
                            Text("Fecha préstamo: ${prestamo.fechaPrestamo}")
                            Text("Fecha devolución: ${prestamo.fechaDevolucion}")
                            Text("Usuario: ${prestamo.nombreUsuario}")
                            Text("Ejemplar: ${prestamo.codigoEjemplar}")
                            Button(onClick = {
                                prestamoAEliminar = prestamo
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
                    prestamoAEliminar?.id?.let { viewModel.eliminarPrestamo(it) }
                    showDialog = false
                    scope.launch { viewModel.getPrestamos() }
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            },
            title = { Text("Eliminar") },
            text = { Text("¿Eliminar préstamo?") }
        )
    }
}
