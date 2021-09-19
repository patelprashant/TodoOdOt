package com.example.todoodot.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoodot.data.models.ToDoData
import com.example.todoodot.databinding.TodoItemLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var dataList = emptyList<ToDoData>()

    class MyViewHolder(private val itemBinding: TodoItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(todoItem: ToDoData) {
            itemBinding.toDoDataItem = todoItem
            itemBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val itemBinding = TodoItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MyViewHolder(itemBinding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentDataItem = dataList[position]
        holder.bind(currentDataItem)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoDataList: List<ToDoData>) {
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoDataList)
        val toDoDiffUtilResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoDataList
        toDoDiffUtilResult.dispatchUpdatesTo(this)
    }
}