package com.example.todoodot.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoodot.R
import com.example.todoodot.data.models.Priority
import com.example.todoodot.data.models.ToDoData
import com.example.todoodot.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object {
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                else -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:navigateToUpdateFragment")
        @JvmStatic
        fun navigateToUpdateFragment(view: RecyclerView, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
                }
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> view.setSelection(0)
                Priority.MEDIUM -> view.setSelection(1)
                Priority.LOW -> view.setSelection(2)
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(view: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> view.setBackgroundColor(view.context.getColor(R.color.red))
                Priority.MEDIUM -> view.setBackgroundColor(view.context.getColor(R.color.orange))
                Priority.LOW -> view.setBackgroundColor(view.context.getColor(R.color.green))
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData){
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }

        }

    }
}