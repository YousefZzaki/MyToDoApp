package com.yz.todoapp.data.viewmodel

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yz.todoapp.R
import com.yz.todoapp.data.models.Priority
import com.yz.todoapp.data.models.ToDoData

class SharedViewModel(application: Application): AndroidViewModel(application) {



    var emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    fun isDatabaseEmpty(toDoData: List<ToDoData>){
        emptyDatabase.value = toDoData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
            }
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
            (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.darkGray))
        }

    }

     fun isDataValid(title: String, description: String): Boolean{
        return title.isNotEmpty() && description.isNotEmpty()
    }

     fun parsePriority(priority: String): Priority {
        return when(priority){
            "High Priority" -> {
                Priority.HIGH}
            "Medium Priority" -> {
                Priority.MEDIUM}
            "Low Priority" -> {
                Priority.LOW}
            else -> Priority.HIGH
        }
    }

    fun parsPriority(priority: Priority): Int{
        return when(priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

    fun parsePriority(priority: Priority): String {
        return when(priority){
            Priority.HIGH ->
                "High Priority"
            Priority.MEDIUM ->
                "Medium Priority"
            Priority.LOW ->
                "Low Priority"
        }
    }

}