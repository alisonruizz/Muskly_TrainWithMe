package com.example.muskly_trainwithme

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme
import java.time.DayOfWeek
import java.time.LocalDate

// Modelo de reto
data class Goal(
    val id: Int,
    val description: String,
    val reward: Int,
    var completed: Boolean = false
)

class GoalsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Muskly_TrainWithMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GoalsScreen(
                        onRewardEarned = { reward ->
                            Toast.makeText(
                                this,
                                "¡Congratulations, you earned $reward chigui-coins!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GoalsScreen(onRewardEarned: (Int) -> Unit) {
    // Lista de 7 retos
    val initialGoals = rememberSaveable {
        listOf(
            Goal(1, "Do 50 squads", 10),
            Goal(2, "Do over 2 hours of training", 15),
            Goal(3, "Have 5 day streak", 25),
            Goal(4, "Train all the muscles in a week", 30),
            Goal(5, "Have a 14 day streak", 50),
            Goal(6, "Do 100 push-ups", 20),
            Goal(7, "Run 10 km", 40)
        )
    }

    var goals by remember { mutableStateOf(initialGoals) }

    // Resetear retos cada lunes
    val today = LocalDate.now().dayOfWeek
    if (today == DayOfWeek.MONDAY) {
        goals = initialGoals.map { it.copy(completed = false) }
    }

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
            // Parte superior con chigüiro y burbuja
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
                        .background(MaterialTheme.colorScheme.surface, shape = speechBubbleShape())
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

            Text(
                text = "Your goals",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                goals.forEachIndexed { index, goal ->
                    GoalItem(
                        goal = goal,
                        onClick = {
                            if (!goal.completed) {
                                val updated = goals.toMutableList()
                                updated[index] = goal.copy(completed = true)
                                goals = updated
                                onRewardEarned(goal.reward)
                            }
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
                color = if (goal.completed) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondary,
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
            color = if (goal.completed) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.secondaryContainer
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
                    color = MaterialTheme.colorScheme.secondaryContainer
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

// Forma de la burbuja de diálogo
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GoalsScreenPreview() {
    Muskly_TrainWithMeTheme {
        GoalsScreen(onRewardEarned = {})
    }
}
