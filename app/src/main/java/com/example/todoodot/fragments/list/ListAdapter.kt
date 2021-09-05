package com.example.todoodot.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoodot.R
import com.example.todoodot.data.models.Priority
import com.example.todoodot.data.models.ToDoData
import com.example.todoodot.databinding.TodoItemLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var dataList = emptyList<ToDoData>()
    private lateinit var bindig: TodoItemLayoutBinding

    class MyViewHolder(private val itemBinding: TodoItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(todoItem: ToDoData) {
            itemBinding.todoTitleText.text = todoItem.title
            itemBinding.todoDescText.text = todoItem.description

            when (todoItem.priority) {
                Priority.HIGH -> itemBinding.todoPriorityIndicator.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.purple_700
                    )
                )
                Priority.MEDIUM -> itemBinding.todoPriorityIndicator.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.purple_500
                    )
                )
                else -> itemBinding.todoPriorityIndicator.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.purple_200
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = TodoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val toDoItem = dataList[position]
        holder.bind(toDoItem)

//        val todoTitleTextView = holder.itemView.findViewById<TextView>(R.id.todo_title_text)
//        todoTitleTextView?.text = toDoItem.title
//
//        val todoDescriptionText = holder.itemView.findViewById<TextView>(R.id.todo_desc_text)
//        todoDescriptionText?.text = toDoItem.description

//        val todoPriorityIndicator = holder.itemView.findViewById<CardView>(R.id.todo_priority_indicator)
//        when (toDoItem.priority) {
//            Priority.HIGH -> todoPriorityIndicator?.setBackgroundColor(
//                ContextCompat.getColor(
//                    holder.itemView.context,
//                    R.color.purple_700
//                )
//            )
//            Priority.MEDIUM -> todoPriorityIndicator?.setBackgroundColor(
//                ContextCompat.getColor(
//                    holder.itemView.context,
//                    R.color.purple_500
//                )
//            )
//            else -> todoPriorityIndicator?.setBackgroundColor(
//                ContextCompat.getColor(
//                    holder.itemView.context,
//                    R.color.purple_200
//                )
//            )
//        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoDataList: List<ToDoData>) {
        this.dataList = toDoDataList
        notifyDataSetChanged()
    }
}