package com.example.todoodot.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoodot.R
import com.example.todoodot.data.models.ToDoData
import com.example.todoodot.data.viewmodel.ToDoViewModel
import com.example.todoodot.databinding.FragmentAddBinding
import com.example.todoodot.fragments.SharedViewModel

class AddFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private var _binding:FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        //Set option menu
        setHasOptionsMenu(true)
        binding.todoPriority.onItemSelectedListener = mSharedViewModel.spinnerListener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertToDoData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertToDoData() {
        //Get values from UI
//        val mTitle = view?.findViewById<EditText>(R.id.todo_title).toString()
        val mTitle = binding.todoTitle.text.toString()
        val mPriority = binding.todoPriority.selectedItem.toString()
        val mDescription = binding.todoDescription.text.toString()

        val validation = mSharedViewModel.verifyUserInput(mTitle, mDescription)

        if (validation) {
            // Insert Data to Database
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )

            mToDoViewModel.insertData(newData)

            //Feedback to User
            Toast.makeText(context, "Successfully added To-Do Data", Toast.LENGTH_SHORT).show()

            //Navigate back to list
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            //Feedback to User
            Toast.makeText(context, "Please Enter data", Toast.LENGTH_SHORT).show()

        }
    }
}