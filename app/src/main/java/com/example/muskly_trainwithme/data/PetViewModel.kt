package com.example.muskly_trainwithme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muskly_trainwithme.data.Mascota
import com.example.muskly_trainwithme.repository.MascotaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MascotaViewModel(private val repository: MascotaRepository) : ViewModel() {

    // Mascota observable en tiempo real (Flow -> StateFlow)
    val mascota = repository.mascota
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Insertar una nueva mascota
    fun insertarMascota(nombre: String) {
        viewModelScope.launch {
            repository.insertar(Mascota(nombre = nombre))
        }
    }

    // Actualizar monedas o experiencia
    fun actualizarMonedasYExp(experiencia: Int, monedas: Int) {
        val currentMascota = mascota.value
        if (currentMascota != null) {
            val updatedMascota = currentMascota.copy(
                experiencia = currentMascota.experiencia + experiencia,
                monedas = currentMascota.monedas + monedas
            )
            viewModelScope.launch {
                repository.actualizar(updatedMascota)
            }
        }
    }

    // Resetear mascota si lo deseas
    fun eliminarMascota() {
        viewModelScope.launch {
            repository.eliminarTodo()
        }
    }
}
