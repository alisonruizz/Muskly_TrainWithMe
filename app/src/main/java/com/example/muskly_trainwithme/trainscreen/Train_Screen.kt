package com.example.muskly_trainwithme.trainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muskly_trainwithme.R
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun trainScreen(viewModel: trainingViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var petName by rememberSaveable { mutableStateOf("") }
    var selectedDay by rememberSaveable { mutableStateOf<String?>(null) }
    var showForm by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Create your new routine")},
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,

                ),

            )
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .clickable(
                    // Si tocas fuera del campo, quita el foco del teclado
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { focusManager.clearFocus() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de mascota
            Image(
                painter = painterResource(id = R.drawable.musktrain),
                contentDescription = "Mascot",
                modifier = Modifier
                    .height(140.dp)
                    //.padding(8.dp)
            )

            // Tarjeta principal
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
            ) {
                Column(Modifier.padding(16.dp)) {

                    // Nombre mascota
                    OutlinedTextField(
                        value = petName,
                        onValueChange = { petName = it },
                        label = { Text("Pet name") },
                        shape = CircleShape, // Bordes redondeados
                        colors = TextFieldDefaults.colors(
                            // Color del contenedor cuando no está enfocado
                            unfocusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                            // Color del contenedor cuando está enfocado
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            //.background(MaterialTheme.colorScheme.outlineVariant)
                            .padding(vertical = 4.dp),


                    )


                    Spacer(Modifier.height(12.dp))

                    // Días de la semana
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                            Button(
                                onClick = { selectedDay = day },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedDay == day)
                                        MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier.padding(horizontal = 2.dp)
                            ) {
                                Text(day, fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.outline)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Lista de ejercicios
                    selectedDay?.let { day ->

                        val routines by viewModel.routines.collectAsState()
                        val exercises = routines[selectedDay] ?: emptyList()

                        if (exercises.isEmpty()) {
                            Text("No exercises added for $day", color = Color.Gray)
                        } else {
                            exercises.forEachIndexed { index, exercise ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(exercise.name, fontWeight = FontWeight.Bold)
                                            Text("${exercise.series}x${exercise.reps} @ ${exercise.weight}kg")
                                        }
                                        IconButton(onClick = {
                                            // Protección: no intentar borrar si el índice ya no existe
                                                viewModel.removeExercise(day, index)

                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = { showForm = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add exercise")
                        }
                    }
                }
            }
        }

        // Diálogo fuera del layout principal
        if (showForm && selectedDay != null) {
            AddExerciseDialog(
                onDismiss = { showForm = false },
                onSave = { exercise ->
                    viewModel.addExercise(selectedDay!!, exercise)
                    showForm = false
                }
            )
        }
    }
}


@Composable
fun AddExerciseDialog(onDismiss: () -> Unit, onSave: (Exercise) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var series by rememberSaveable { mutableStateOf("") }
    var reps by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank() && series.isNotBlank() && reps.isNotBlank() && weight.isNotBlank()) {
                    onSave(Exercise(name, series.toInt(), reps.toInt(), weight.toInt()))
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("Add Exercise") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Exercise name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    )
                )
                OutlinedTextField(
                    value = series,
                    onValueChange = { if (it.all { ch -> ch.isDigit() }) series = it },
                    label = { Text("Series") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = reps,
                    onValueChange = { if (it.all { ch -> ch.isDigit() }) reps = it },
                    label = { Text("Reps") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = weight,
                    onValueChange = { if (it.all { ch -> ch.isDigit() }) weight = it },
                    label = { Text("Weight (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun trainPreview() {
    Muskly_TrainWithMeTheme {
        trainScreen()
    }
}
