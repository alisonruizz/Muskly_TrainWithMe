package com.example.muskly_trainwithme.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Mascota::class], version = 1, exportSchema = false)
abstract class MascotaDatabase : RoomDatabase() {
    abstract fun mascotaDao(): MascotaDao

    companion object {
        @Volatile
        private var INSTANCE: MascotaDatabase? = null

        fun getDatabase(context: Context): MascotaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MascotaDatabase::class.java,
                    "mascota_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
