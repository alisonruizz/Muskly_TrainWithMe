package com.example.muskly_trainwithme.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MascotaDao {

    @Query("SELECT * FROM mascota LIMIT 1")
    fun getMascota(): Flow<Mascota?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMascota(mascota: Mascota)

    @Update
    suspend fun updateMascota(mascota: Mascota)

    @Query("DELETE FROM mascota")
    suspend fun deleteAll()
}
