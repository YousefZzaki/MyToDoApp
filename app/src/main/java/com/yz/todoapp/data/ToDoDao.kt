package com.yz.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yz.todoapp.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM `todo-table` ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDoData: ToDoData)

    @Update
    suspend fun update(toDoData: ToDoData)

    @Delete
    suspend fun delete(toDoData: ToDoData)

    @Query("DELETE FROM `todo-table`")
    suspend fun deleteAll()

    @Query("SELECT * FROM `todo-table` WHERE title LIKE :searchQuery")
    fun search(searchQuery: String) : LiveData<List<ToDoData>>

    @Query("SELECT * FROM `todo-table` ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM `todo-table` ORDER BY CASE WHEN priority LIKE 'H%' THEN 3 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 1 END")
    fun sortByLowPriority(): LiveData<List<ToDoData>>
}