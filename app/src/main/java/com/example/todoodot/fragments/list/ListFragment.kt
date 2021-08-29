package com.example.todoodot.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoodot.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val addTodoBtn = view.findViewById<FloatingActionButton>(R.id.addTodo)
        addTodoBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        val listOfTodos = view.findViewById<RecyclerView>(R.id.listOfTodos)
        listOfTodos.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }


        return view
    }
}