package com.example.proyectofinal.DropMenus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.proyectofinal.model.Ejemplar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuEjemplar(
    ejemplares: List<Ejemplar>,
    ejemplarSeleccionado: Ejemplar?,
    onEjemplarSelected: (Ejemplar) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            readOnly = true,
            value = ejemplarSeleccionado?.codigo ?: "Seleccionar ejemplar",
            onValueChange = {},
            label = { Text("Ejemplar") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ejemplares.forEach { ejemplar ->
                DropdownMenuItem(
                    text = { Text(ejemplar.codigo) },
                    onClick = {
                        onEjemplarSelected(ejemplar)
                        expanded = false
                    }
                )
            }
        }
    }
}

