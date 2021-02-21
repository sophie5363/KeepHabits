package com.example.keephabits.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.keephabits.data.models.Habit
import com.example.keephabits.logic.dao.HabitDao
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitDatabase: RoomDatabase() {

    abstract fun habitDao() : HabitDao

    companion object{
        @Volatile
        private var INSTANCE:HabitDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context): HabitDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}