package com.example.todoodot.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoodot.R
import com.example.todoodot.data.models.Priority
import com.example.todoodot.data.models.ToDoData
import com.example.todoodot.data.viewmodel.ToDoViewModel

class AddFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //Set option menu
        setHasOptionsMenu(true)

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
        val mTitle = view?.findViewById<EditText>(R.id.todo_title).toString()
        val mPriority = view?.findViewById<Spinner>(R.id.todo_priority).toString()
        val mDescription = view?.findViewById<EditText>(R.id.todo_description).toString()

        val validation = verifyUserInput(mTitle, mDescription)

        if (validation) {
            // Insert Data to Database
            val newData = ToDoData(
                0,
                mTitle,
                parsePriority(mPriority),
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

    private fun verifyUserInput(title: String, desc: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc)) {
            false
        } else !(title.isEmpty() || desc.isEmpty())
    }

    private fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> (Priority.HIGH)
            "Medium Priority" -> (Priority.MEDIUM)
            else -> (Priority.LOW)
        }
    }
}