package com.example.todoodot.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoodot.R
import com.example.todoodot.data.viewmodel.ToDoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set option menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val todoListRecyclerView = view.findViewById<RecyclerView>(R.id.list_of_todos)
        todoListRecyclerView.adapter = adapter
        todoListRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })

        val addTodoBtn = view.findViewById<FloatingActionButton>(R.id.add_todo)
        addTodoBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        val listOfTodos = view.findViewById<RecyclerView>(R.id.list_of_todos)
        listOfTodos.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}