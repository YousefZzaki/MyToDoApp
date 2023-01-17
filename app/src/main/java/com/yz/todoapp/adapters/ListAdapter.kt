package com.yz.todoapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yz.todoapp.data.models.ToDoData
import com.yz.todoapp.databinding.RowLayoutBinding
import com.yz.todoapp.fragments.list.ListFragmentDirections
import com.yz.todoapp.utils.DiffUtil

class ListAdapter: RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class ListViewHolder(private val item: RowLayoutBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(toDoData: ToDoData){
            item.toDoData = toDoData
            item.executePendingBindings()
        }
        val rowBackground = item.rowBackground

        companion object{
            fun from(parent: ViewGroup): ListViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val item = dataList[position]
        holder.bind(item)
        val action = ListFragmentDirections.actionListFragmentToUpdateFragment(item)
        holder.rowBackground.setOnClickListener {
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(dataList: List<ToDoData>) {
        val diffUtil = DiffUtil(this.dataList, dataList)
        val diffUtilResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        this.dataList = dataList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}