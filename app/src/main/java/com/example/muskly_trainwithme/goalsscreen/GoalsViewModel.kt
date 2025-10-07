package com.example.muskly_trainwithme.goalsscreen


import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.time.DayOfWeek
import java.time.LocalDate


class GoalsViewModel : ViewModel() {

    // Lista inicial de retos
    private val initialGoals = listOf(
        Goal(1, "Do 50 squads", 10),
        Goal(2, "Do over 2 hours of training", 15),
        Goal(3, "Have 5 day streak", 25),
        Goal(4, "Train all the muscles in a week", 30),
        Goal(5, "Have a 14 day streak", 50),
        Goal(6, "Do 100 push-ups", 20),
        Goal(7, "Run 10 km", 40)
    )

    // Estado observable de la lista
    private val _goals = mutableStateListOf<Goal>().apply { addAll(initialGoals) }
    val goals: List<Goal> get() = _goals

    // Estado de monedas ganadas (opcional, si quieres acumularlas aquí)
    var coins = mutableStateOf(0)
        private set

    init {
        resetGoalsIfMonday()
    }

    // Marca un reto como completado
    fun completeGoal(goal: Goal, onRewardEarned: (Int) -> Unit) {
        val index = _goals.indexOfFirst { it.id == goal.id }
        if (index != -1 && !_goals[index].completed) {
            _goals[index] = _goals[index].copy(completed = true)
            coins.value += goal.reward
            onRewardEarned(goal.reward)
        }
    }

    // Reinicia los retos cada lunes
    private fun resetGoalsIfMonday() {
        val today = LocalDate.now().dayOfWeek
        if (today == DayOfWeek.MONDAY) {
            for (i in _goals.indices) {
                _goals[i] = _goals[i].copy(completed = false)
            }
        }
    }

    // Permite reiniciar manualmente (útil para debug)
    fun resetAllGoals() {
        for (i in _goals.indices) {
            _goals[i] = _goals[i].copy(completed = false)
        }
    }
}
