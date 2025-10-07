package com.example.muskly_trainwithme.trainscreen

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class trainingViewModel : ViewModel() {
    // Map<day, list of exercises>
    var routines = mutableStateMapOf<String, MutableList<Exercise>>()
        private set

    fun addExercise(day: String, exercise: Exercise) {
        val list = routines.getOrPut(day) { mutableListOf() }
        list.add(exercise)
    }

    fun removeExercise(day: String, index: Int) {
        routines[day]?.removeAt(index)
    }
}

