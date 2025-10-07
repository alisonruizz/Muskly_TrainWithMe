package com.example.muskly_trainwithme.repository

import com.example.muskly_trainwithme.data.Mascota
import com.example.muskly_trainwithme.data.MascotaDao
import kotlinx.coroutines.flow.Flow

class MascotaRepository(private val dao: MascotaDao) {
    val mascota: Flow<Mascota?> = dao.getMascota()

    suspend fun insertar(mascota: Mascota) = dao.insertMascota(mascota)
    suspend fun actualizar(mascota: Mascota) = dao.updateMascota(mascota)
    suspend fun eliminarTodo() = dao.deleteAll()
}
