package com.example.todoodot.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.todoodot.R
import com.example.todoodot.data.models.Priority
import com.example.todoodot.databinding.FragmentUpdateBinding
import com.example.todoodot.fragments.SharedViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Set option menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.currentTodoTitle.setText(args.currentToDoItem.title)
        binding.currentTodoDescription.setText(args.currentToDoItem.description)
        binding.currentTodoPriority.setSelection(parsePriority(args.currentToDoItem.priority))
        binding.currentTodoPriority.onItemSelectedListener = mSharedViewModel.spinnerListener

        return view
    }

    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }
}