package com.example.muskly_trainwithme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource



data class Tip(val title: String, val short: String, val details: String)

class TipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TipsScreen()
            }
        }
    }
}

@Composable
fun TipsScreen() {
    // Estado global: guarda el índice del consejo expandido
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    val tipsCategories = listOf(
        "Exercise" to listOf(
            "Warm up 5–10 min" to "Before lifting heavy or doing intense cardio, always warm up 5–10 minutes to prepare your muscles and joints.",
            "Progressive overload" to "Gradually increase weight, reps, or intensity to keep making progress. Small improvements add up!",
            "Don’t train same muscles daily" to "Give your muscles at least 48h to recover before hitting them again.",
            "Compound lifts matter" to "Squats, deadlifts, bench press, and pull-ups train multiple muscles at once. Use them as the base of your routine.",
            "Change routine every 6–8 weeks" to "Your body adapts quickly. Add variation to avoid plateaus.",
            "Don’t go to failure every set" to "Save failure training for your last set of an exercise.",
            "Cardio is also important" to "Even if your goal is muscle growth, cardio keeps your heart healthy and improves recovery.",
            "Track more than weight" to "Measure strength, endurance, and body composition, not just the scale.",
            "Stay hydrated" to "Drink water during training to avoid cramps and fatigue.",
            "Consistency beats intensity" to "Training regularly is more important than going all-out once in a while."
        ),
        "Technique" to listOf(
            "Form over weight" to "Better to lift less but correctly. Good form prevents injuries and maximizes gains.",
            "Breathe correctly" to "Inhale when lowering, exhale when lifting. Don’t hold your breath unless using bracing.",
            "Engage your core" to "A strong core stabilizes your spine and improves almost every lift.",
            "Control the movement" to "Avoid bouncing or using too much momentum. Time under tension builds strength.",
            "Full range of motion" to "When safe, use the complete ROM to maximize muscle activation.",
            "Stop if sharp pain" to "Discomfort is fine, sharp pain is not. Know the difference.",
            "Adjust equipment" to "Make sure machines are set to your body size to avoid bad mechanics.",
            "Don’t overuse the mirror" to "Check form if needed, but also learn to feel the movement.",
            "Master basics first" to "Squats, deadlifts, and presses are fundamental—learn them well.",
            "Stretch smart" to "Dynamic stretches before training, static stretches after training."
        ),
        "Rest" to listOf(
            "Muscle grows while resting" to "Recovery is where the real gains happen, not in the gym.",
            "Sleep 7–9h" to "Lack of sleep kills recovery, performance, and motivation.",
            "Don’t train right before bed" to "Hard training close to bedtime can interfere with quality sleep.",
            "Respect rest days" to "They are part of the program, not laziness.",
            "Stretch after training" to "Helps reduce stiffness and keeps mobility.",
            "Use deload weeks" to "Every 6–8 weeks, reduce load/volume to recover long-term.",
            "Active recovery works" to "Walking, swimming, or yoga on rest days helps circulation and repair.",
            "Avoid too much caffeine" to "Stimulants late in the day ruin sleep quality.",
            "Nutrition = recovery" to "Eat enough protein and carbs to rebuild muscles.",
            "Listen to your body" to "If you’re exhausted, skipping one workout may be better than pushing through."
        )
    )
    val categories = tipsCategories.map { (category, tips) ->
        category to tips.shuffled().take(2)
    }

    var selectedTab by remember { mutableStateOf(2) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagen_2025_08_24_143956030_png),
                contentDescription = "Capybara mascot",
                modifier = Modifier
                    .size(110.dp)
                    .padding(bottom = 8.dp)
            )
            // Titulo centrado
            Text(
                text = "Tips and advices",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 18.dp)
            )

            categories.forEachIndexed { categoryIndex, (categoryName, tips) ->
                // Nombre de la categoría
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 8.dp)
                )

                tips.forEachIndexed { tipIndex, (shortText, detailedText) ->
                    val index = categoryIndex * 10 + tipIndex // id único para cada consejo

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = shortText,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                IconButton(
                                    onClick = {
                                        expandedIndex = if (expandedIndex == index) null else index
                                    },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Expand",
                                        tint = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                }
                            }

                            if (expandedIndex == index) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = detailedText,
                                    fontSize = 14.sp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun tipsPreview() {
    Muskly_TrainWithMeTheme {
        TipsScreen()

    }
}

