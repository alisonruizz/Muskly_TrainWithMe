package com.example.muskly_trainwithme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muskly_trainwithme.repository.MascotaRepository

class MascotaViewModelFactory(private val repository: MascotaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MascotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MascotaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
