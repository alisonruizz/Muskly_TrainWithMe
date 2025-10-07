package com.example.muskly_trainwithme.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mascota")
data class Mascota(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val monedas: Int = 0,
    val experiencia: Int = 0
)