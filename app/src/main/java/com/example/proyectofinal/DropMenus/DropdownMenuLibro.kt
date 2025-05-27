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
import com.example.proyectofinal.model.Libro

@Composable
fun DropdownMenuLibro(
    libros: List<Libro>,
    libroSeleccionado: Libro?,
    onLibroSeleccionado: (Libro) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(libroSeleccionado?.titulo ?: "Seleccionar libro")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            libros.forEach { libro ->
                DropdownMenuItem(
                    text = { Text(libro.titulo ?: "Sin título") },
                    onClick = {
                        onLibroSeleccionado(libro)
                        expanded = false
                    }
                )
            }
        }
    }
}
