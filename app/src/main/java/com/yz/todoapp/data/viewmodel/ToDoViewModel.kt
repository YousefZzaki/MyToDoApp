package com.yz.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.yz.todoapp.data.ToDoDao
import com.yz.todoapp.data.ToDoDatabase
import com.yz.todoapp.data.models.ToDoData
import com.yz.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) {

    private var toDoDao: ToDoDao

    private var repository: ToDoRepository

    var getAllData: LiveData<List<ToDoData>>

    val sortByHighest: LiveData<List<ToDoData>>
    val sortByLowest: LiveData<List<ToDoData>>

    init {
        toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData

        sortByHighest = repository.sortByHighest
        sortByLowest = repository.sortByLowest

    }

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun update(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun search(searchQuery: String): LiveData<List<ToDoData>> {
        return repository.searchDatabase(searchQuery)
    }

}