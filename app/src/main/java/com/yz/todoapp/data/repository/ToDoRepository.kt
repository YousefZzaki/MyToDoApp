package com.yz.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.yz.todoapp.data.ToDoDao
import com.yz.todoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao){

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    val sortByHighest: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val sortByLowest: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insert(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.update(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData){
        toDoDao.delete(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }

     fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>{
        return toDoDao.search(searchQuery)
    }
}