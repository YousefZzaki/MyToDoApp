package com.yz.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yz.todoapp.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 3)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase(){

    abstract fun toDoDao(): ToDoDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoDatabase::class.java,
                        "todo_database"
                    ).fallbackToDestructiveMigration().build()
                }
                return INSTANCE!!
            }
            return INSTANCE!!
        }
    }
}