package com.example.todoodot.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoodot.R
import com.example.todoodot.data.models.Priority
import com.example.todoodot.data.models.ToDoData

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var dataList = emptyList<ToDoData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.todo_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val todoTitleTextView = holder.itemView.findViewById<TextView>(R.id.todo_title_text)
        todoTitleTextView?.text = dataList[position].title

        val todoDescriptionText = holder.itemView.findViewById<TextView>(R.id.todo_desc_text)
        todoDescriptionText?.text = dataList[position].description

        val todoPriorityIndicator = holder.itemView.findViewById<CardView>(R.id.todo_priority_indicator)
        when (dataList[position].priority) {
            Priority.HIGH -> todoPriorityIndicator?.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.purple_700
                )
            )
            Priority.MEDIUM -> todoPriorityIndicator?.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.purple_500
                )
            )
            else -> todoPriorityIndicator?.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.purple_200
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoDataList: List<ToDoData>) {
        this.dataList = toDoDataList
        notifyDataSetChanged()
    }
}