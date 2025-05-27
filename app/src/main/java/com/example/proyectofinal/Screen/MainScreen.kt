package com.example.proyectofinal.Screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    var pantallaActual by remember { mutableStateOf("libro") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.background_uno),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Contenido sobre la imagen
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
        ) {
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Button(onClick = { pantallaActual = "libro" }) {
                    Text("Libros")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { pantallaActual = "autor" }) {
                    Text("Autores")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { pantallaActual = "categoria" }) {
                    Text("Categorías")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { pantallaActual = "usuario" }) {
                    Text("Usuarios")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { pantallaActual = "ejemplar" }) {
                    Text("Ejemplares")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { pantallaActual = "prestamo" }) {
                    Text("Préstamos")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (pantallaActual) {
                "libro" -> LibroScreen()
                "autor" -> AutorScreen()
                "categoria" -> CategoriaScreen()
                "usuario" -> UsuarioScreen()
                "ejemplar" -> EjemplarScreen()
                "prestamo" -> PrestamoScreen()
            }
        }
    }
}