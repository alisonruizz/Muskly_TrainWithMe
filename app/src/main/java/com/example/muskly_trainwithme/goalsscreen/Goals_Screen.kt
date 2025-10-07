package com.example.muskly_trainwithme.goalsscreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.muskly_trainwithme.R
import com.example.muskly_trainwithme.data.MascotaDatabase
import com.example.muskly_trainwithme.repository.MascotaRepository
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme
import com.example.muskly_trainwithme.viewmodel.MascotaViewModel
import com.example.muskly_trainwithme.viewmodel.MascotaViewModelFactory

// Modelo de reto
data class Goal(
    val id: Int,
    val description: String,
    val reward: Int,
    var completed: Boolean = false
)

class GoalsActivity : ComponentActivity() {

    private lateinit var mascotaViewModel: MascotaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar DB y ViewModel de Mascota
        val database = MascotaDatabase.getDatabase(this)
        val repository = MascotaRepository(database.mascotaDao())
        val factory = MascotaViewModelFactory(repository)
        mascotaViewModel = ViewModelProvider(this, factory)[MascotaViewModel::class.java]

        setContent {
            Muskly_TrainWithMeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GoalsScreen(
                        mascotaViewModel = mascotaViewModel,
                        onRewardEarned = { reward ->
                            mascotaViewModel.actualizarMonedasYExp(experiencia = 10, monedas = reward)
                            Toast.makeText(
                                this,
                                "🎉 You earned $reward chigui-coins!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GoalsScreen(
    mascotaViewModel: MascotaViewModel,
    onRewardEarned: (Int) -> Unit,
    goalsViewModel: GoalsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val goals = goalsViewModel.goals
    val mascota by mascotaViewModel.mascota.collectAsState(initial = null)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔹 Parte superior (imagen + burbuja)
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.goals_png),
                    contentDescription = "Mascota",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )

                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = speechBubbleShape()
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.musk_message),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Info de la mascota
            mascota?.let {
                Text(
                    text = "Hi ${it.nombre}! You have ${it.monedas} 🪙 and ${it.experiencia} XP",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your goals",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Lista de retos
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                goals.forEach { goal ->
                    GoalItem(
                        goal = goal,
                        onClick = {
                            goalsViewModel.completeGoal(goal, onRewardEarned)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GoalItem(goal: Goal, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (goal.completed)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = goal.description,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f),
            color = if (goal.completed)
                MaterialTheme.colorScheme.onPrimaryContainer
            else
                MaterialTheme.colorScheme.onSecondaryContainer
        )

        if (!goal.completed) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${goal.reward}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Image(
                    painter = painterResource(id = R.drawable.chiguicoin_png),
                    contentDescription = "Moneda",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// 🔹 Forma de la burbuja
fun speechBubbleShape(): GenericShape {
    return GenericShape { size, _ ->
        val cornerRadius = 40f
        val pointerSize = 40f

        moveTo(cornerRadius, 0f)
        lineTo(size.width - cornerRadius, 0f)
        quadraticBezierTo(size.width, 0f, size.width, cornerRadius)
        lineTo(size.width, size.height - cornerRadius)
        quadraticBezierTo(size.width, size.height, size.width - cornerRadius, size.height)
        lineTo(pointerSize + cornerRadius, size.height)
        lineTo(pointerSize / 2, size.height + pointerSize)
        lineTo(cornerRadius, size.height)
        quadraticBezierTo(0f, size.height, 0f, size.height - cornerRadius)
        lineTo(0f, cornerRadius)
        quadraticBezierTo(0f, 0f, cornerRadius, 0f)
        close()
    }
}

