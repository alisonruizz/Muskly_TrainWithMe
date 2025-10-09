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
        // Contenido principal
        AppNavigation(modifier = Modifier.fillMaxSize())

        // Ícono del signo de pregunta para la descripción y créditos
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
                    Text("About the app", fontSize = 20.sp)
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //Descripción
                        Text(
                            text = "Muskly is an app to keep you motivated at the gym with the help of a virtual pet. Track your routines and complete challenges to boost your progress. " +
                                    "By completing workouts and challenges, you help your pet improve its fitness. The points you earn can be used to unlock clothing, accessories, and skins, making training more fun and motivating.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        //Créditos
                        Text(
                            text = "Credits:\nAlison Daniela Ruiz\nJuan José Ángel Durán",
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
