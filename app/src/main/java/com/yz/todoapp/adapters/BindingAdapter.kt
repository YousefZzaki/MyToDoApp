package com.yz.todoapp.adapters

import android.view.View
import android.widget.CalendarView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yz.todoapp.R
import com.yz.todoapp.data.models.Priority
import com.yz.todoapp.fragments.list.ListFragmentDirections
import com.yz.todoapp.fragments.update.UpdateFragmentDirections

class BindingAdapter {

    companion object{

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, boolean: Boolean){
            if (boolean){
                view.setOnClickListener {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:onDatabaseEmpty")
        @JvmStatic
        fun onDatabaseEmpty(view: View, emptyDatabase: MutableLiveData<Boolean>){
           when(emptyDatabase.value){
               true -> view.visibility = View.VISIBLE
               false -> view.visibility = View.INVISIBLE
               else -> {}
           }
        }

        @BindingAdapter("android:parsPriorityColor")
        @JvmStatic
        fun parsPriorityColor(view: CardView, priority: Priority){
            when(priority){
                Priority.HIGH -> view.setCardBackgroundColor(view.context.getColor(R.color.red))
                Priority.MEDIUM -> view.setCardBackgroundColor(view.context.getColor(R.color.yellow))
                Priority.LOW -> view.setCardBackgroundColor(view.context.getColor(R.color.green))
            }
        }

        @BindingAdapter("android:navigateToUpdateFragment")
        @JvmStatic
        fun navigateToUpdateFragment(layout: ConstraintLayout, boolean: Boolean){
            layout.setOnClickListener {
                layout.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
            }
        }
    }
}