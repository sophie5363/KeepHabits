package com.example.keephabits.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.keephabits.data.database.HabitDatabase
import com.example.keephabits.data.models.Habit
import com.example.keephabits.logic.repository.HabitRepository
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository
    val getAllHabits : LiveData<List<Habit>>

    init {
        val habitDao = HabitDatabase.getDatabase(application).habitDao()
        repository = HabitRepository(habitDao)

        getAllHabits = repository.getAllHabits
    }

}