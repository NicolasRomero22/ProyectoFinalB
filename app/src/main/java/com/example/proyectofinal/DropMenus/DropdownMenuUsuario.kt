package com.example.proyectofinal.DropMenus

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.proyectofinal.model.Usuario
@Composable
fun DropdownMenuUsuario(
    usuarios: List<Usuario>,
    usuarioSeleccionado: Usuario?,
    onUsuarioSeleccionado: (Usuario) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(usuarioSeleccionado?.nombre ?: "Seleccionar usuario")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            usuarios.forEach { usuario ->
                DropdownMenuItem(
                    text = { Text(usuario.nombre ?: "Sin nombre") },
                    onClick = {
                        onUsuarioSeleccionado(usuario)
                        expanded = false
                    }
                )
            }
        }
    }
}
