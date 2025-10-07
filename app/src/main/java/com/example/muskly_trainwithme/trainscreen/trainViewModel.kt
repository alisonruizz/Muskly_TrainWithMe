package com.example.muskly_trainwithme.trainscreen

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class trainingViewModel : ViewModel() {
    private val _routines = MutableStateFlow<Map<String, List<Exercise>>>(emptyMap())
    val routines: StateFlow<Map<String, List<Exercise>>> = _routines

    fun addExercise(day: String, exercise: Exercise) {
        val updated = _routines.value.toMutableMap()
        val dayList = updated[day]?.toMutableList() ?: mutableListOf()
        dayList.add(exercise)
        updated[day] = dayList
        _routines.value = updated
    }

    fun removeExercise(day: String, index: Int) {
        val updated = _routines.value.toMutableMap()
        val dayList = updated[day]?.toMutableList() ?: return
        dayList.removeAt(index)
        updated[day] = dayList
        _routines.value = updated
    }
}

