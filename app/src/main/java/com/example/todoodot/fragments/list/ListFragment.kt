package com.example.todoodot.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoodot.R
import com.example.todoodot.data.viewmodel.ToDoViewModel
import com.example.todoodot.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Set option menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })

        binding.listOfTodos.adapter = adapter
        binding.listOfTodos.layoutManager = LinearLayoutManager(requireActivity())

        binding.listOfTodos.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        binding.addTodo.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            deleteAllData()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun deleteAllData() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Delete All Data")
        builder.setMessage("Are you sure you want to delete all Data?")

        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAllData()
            //Feedback to User
            Toast.makeText(context, "Successfully deleted All data", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }

        builder.create().show()
    }
}