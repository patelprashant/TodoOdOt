package com.example.todoodot.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoodot.R
import com.example.todoodot.data.models.ToDoData
import com.example.todoodot.data.viewmodel.ToDoViewModel
import com.example.todoodot.databinding.FragmentUpdateBinding
import com.example.todoodot.fragments.SharedViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

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
        binding.currentTodoPriority.setSelection(mSharedViewModel.parsePriority(args.currentToDoItem.priority))
        binding.currentTodoPriority.onItemSelectedListener = mSharedViewModel.spinnerListener

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_update) {
            updateTodoData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateTodoData() {
        val uTitle = binding.currentTodoTitle.text.toString()
        val uDescription = binding.currentTodoDescription.text.toString()
        val uPriority = binding.currentTodoPriority.selectedItem.toString()

        val validation = mSharedViewModel.verifyUserInput(uTitle, uDescription)

        if (validation) {
            // Updated Data to Database
            val updatedData = ToDoData(
                args.currentToDoItem.id,
                uTitle,
                mSharedViewModel.parsePriority(uPriority),
                uDescription
            )

            mToDoViewModel.updateData(updatedData)

            //Feedback to User
            Toast.makeText(context, "Successfully Updated To-Do Data", Toast.LENGTH_SHORT).show()

            //Navigate back to list
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            //Feedback to User
            Toast.makeText(context, "Please Enter data", Toast.LENGTH_SHORT).show()
        }
    }
}