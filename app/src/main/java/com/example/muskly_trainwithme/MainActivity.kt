package com.example.muskly_trainwithme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Muskly_TrainWithMeTheme {
                MainContent()
                Scaffold { paddingValues ->
                    AppNavigation(modifier = androidx.compose.ui.Modifier.padding(paddingValues))
                }
                //trainScreen()
            }
        }
    }
}

@Composable
fun MainContent() {
    var showCredits by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        // Contenido principal (navegación)
        AppNavigation(modifier = Modifier.fillMaxSize())

        // Ícono del signo de pregunta en la esquina superior derecha
        IconButton(
            onClick = { showCredits = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Help,
                contentDescription = "Ayuda / Créditos",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // === Diálogo con créditos ===
        if (showCredits) {
            AlertDialog(
                onDismissRequest = { showCredits = false },
                confirmButton = {
                    Button(
                        onClick = { showCredits = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Text("Cerrar", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                },
                title = {
                    Text("Acerca de la aplicación", fontSize = 20.sp)
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // === Descripción justificada ===
                        Text(
                            text = "Muskly es una app para mantenerte motivado en el gimnasio gracias al acompañamiento de una mascota virtual, permitiendo registrar rutinas y completar retos que impulsen tu progreso. Al completar entrenamientos y retos, ayudas a su mascota virtual a mejorar su condición, donde los puntos obtenidos sirven para desbloquear ropa, accesorios y apariencias, haciendo el entrenamiento más divertido y motivador.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // === Créditos centrados ===
                        Text(
                            text = "Créditos:\nAlison Daniela Ruiz\nJuan José Ángel Durán",
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}
