package com.example.muskly_trainwithme

import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.painterResource



data class Tip(val category: String, val short: String, val details: String)

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
    var expandedIndex by remember { mutableStateOf<String?>(null) }

    val allTips = listOf(
        Tip("Exercise", "Warm up 5–10 min", "Before lifting heavy or doing intense cardio, always warm up 5–10 minutes to prepare your muscles and joints."),
        Tip("Exercise", "Progressive overload", "Gradually increase weight, reps, or intensity to keep making progress. Small improvements add up!"),
        Tip("Exercise", "Don’t train same muscles daily", "Give your muscles at least 48h to recover before hitting them again."),
        Tip("Exercise", "Compound lifts matter", "Squats, deadlifts, bench press, and pull-ups train multiple muscles at once. Use them as the base of your routine."),
        Tip("Exercise", "Change routine every 6–8 weeks", "Your body adapts quickly. Add variation to avoid plateaus."),
        Tip("Exercise", "Don’t go to failure every set", "Save failure training for your last set of an exercise."),
        Tip("Exercise", "Cardio is also important", "Even if your goal is muscle growth, cardio keeps your heart healthy and improves recovery."),
        Tip("Exercise", "Track more than weight", "Measure s  trength, endurance, and body composition, not just the scale."),
        Tip("Exercise","Stay hydrated", "Drink water during training to avoid cramps and fatigue."),
        Tip("Exercise","Consistency beats intensity", "Training regularly is more important than going all-out once in a while."),
        Tip("Technique", "Form over weight", "Better to lift less but correctly. Good form prevents injuries and maximizes gains."),
        Tip("Technique","Breathe correctly", "Inhale when lowering, exhale when lifting. Don’t hold your breath unless using bracing."),
        Tip("Technique","Engage your core", "A strong core stabilizes your spine and improves almost every lift."),
        Tip("Technique","Control the movement", "Avoid bouncing or using too much momentum. Time under tension builds strength."),
        Tip("Technique","Full range of motion", "When safe, use the complete ROM to maximize muscle activation."),
        Tip("Technique","Stop if sharp pain", "Discomfort is fine, sharp pain is not. Know the difference."),
        Tip("Technique","Adjust equipment", "Make sure machines are set to your body size to avoid bad mechanics."),
        Tip("Technique","Don’t overuse the mirror", "Check form if needed, but also learn to feel the movement."),
        Tip("Technique","Master basics first", "Squats, deadlifts, and presses are fundamental—learn them well."),
        Tip("Technique","Stretch smart", "Dynamic stretches before training, static stretches after training."),
        Tip("Rest", "Muscle grows while resting", "Recovery is where the real gains happen, not in the gym."),
        Tip("Rest", "Sleep 7–9h" ,"Lack of sleep kills recovery, performance, and motivation."),
        Tip("Rest",  "Don’t train right before bed" , "Hard training close to bedtime can interfere with quality sleep."),
        Tip("Rest",   "Respect rest days" , "They are part of the program, not laziness."),
        Tip("Rest",   "Stretch after training" , "Helps reduce stiffness and keeps mobility."),
        Tip("Rest",   "Use deload weeks" , "Every 6–8 weeks, reduce load/volume to recover long-term."),
        Tip("Rest",    "Active recovery works", "Walking, swimming, or yoga on rest days helps circulation and repair."),
        Tip("Rest",   "Avoid too much caffeine", "Stimulants late in the day ruin sleep quality."),
        Tip("Rest",    "Nutrition = recovery", "Eat enough protein and carbs to rebuild muscles."),
        Tip("Rest",    "Listen to your body", "If you’re exhausted, skipping one workout may be better than pushing through.")
    )


    val categories = allTips
        .groupBy { it.category }
        .map { (category, tips) -> category to tips.shuffled().take(2) }


    var selectedTab by remember { mutableStateOf(2) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.tips),
                    contentDescription = "Capybara mascot",
                    modifier = Modifier
                        .size(110.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Tips and advices",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 18.dp)
                )
            }

            categories.forEach{ (categoryName, tips) ->
                item {
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        fontSize = 28.sp,
                        modifier = Modifier

                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }

                itemsIndexed(tips) { tipIndex, tip ->
                    val index = "$categoryName-$tipIndex"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = tip.short,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White

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
                                    text = tip.details,
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

