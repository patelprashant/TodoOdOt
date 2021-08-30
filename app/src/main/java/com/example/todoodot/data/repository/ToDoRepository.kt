package com.example.todoodot.data.repository

import androidx.lifecycle.LiveData
import com.example.todoodot.data.ToDoDao
import com.example.todoodot.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }
}