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
import com.example.proyectofinal.ViewModel.UsuarioViewModel
import com.example.proyectofinal.model.Usuario
import kotlinx.coroutines.launch
@Composable
fun UsuarioScreen(viewModel: UsuarioViewModel = viewModel()) {
    val usuarios by viewModel.usuarios.observeAsState(emptyList())
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var usuarioAEliminar by remember { mutableStateOf<Usuario?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var codigoBusqueda by remember { mutableStateOf("")}
    LaunchedEffect(true) { viewModel.getUsuarios() }
    val librosFiltrados = usuarios.filter {
        it.id?.toString()?.contains(codigoBusqueda, ignoreCase = true) ?: false
    }
    Column(Modifier.padding(16.dp)) {
        Text(
            "Usuario",
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
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })

        Button(onClick = {
            scope.launch {
                viewModel.guardarUsuario(Usuario(nombre = nombre, email = email))
                nombre = ""
                email = ""
                Toast.makeText(context, "Usuario guardado", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Agregar Usuario")
        }
        if (librosFiltrados.isEmpty()) {
            Text("No hay libros disponibles")
        } else {
            LazyColumn {
                items(usuarios) { usuario ->
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text("ID: ${usuario.id}")
                            Text("Nombre: ${usuario.nombre}")
                            Text("Email: ${usuario.email}")
                            Button(onClick = {
                                usuarioAEliminar = usuario
                                showDialog = true
                            }) { Text("Eliminar") }
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
                    usuarioAEliminar?.id?.let { viewModel.eliminarUsuario(it) }
                    showDialog = false
                }) { Text("Sí") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("No") } },
            title = { Text("Eliminar") },
            text = { Text("¿Eliminar usuario?") }
        )
    }
}
