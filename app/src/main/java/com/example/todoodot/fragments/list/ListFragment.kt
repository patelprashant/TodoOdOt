package com.example.todoodot.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoodot.R
import com.example.todoodot.data.models.ToDoData
import com.example.todoodot.data.viewmodel.ToDoViewModel
import com.example.todoodot.databinding.FragmentListBinding
import com.example.todoodot.fragments.SharedViewModel
import com.example.todoodot.fragments.list.adapter.ListAdapter
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    internal val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    internal val adapter: ListAdapter by lazy { ListAdapter() }
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
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel
        val view = binding.root

        //Setup recyclerview
        setupRecyclerView()

        // Observe liveData
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

//        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
//            showEmptyDatabaseViews(it)
//        })


//        binding.addTodo.setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_addFragment)
//        }

        return view
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.listOfTodos

        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 200
        }

        swipeToDelete(recyclerView)
//
//        binding.listOfTodos.setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
//        }
    }

//    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
//        if (emptyDatabase) {
//            binding.noDataImageView.visibility = View.VISIBLE
//            binding.noDataTextView.visibility = View.VISIBLE
//        } else {
//            binding.noDataImageView.visibility = View.INVISIBLE
//            binding.noDataTextView.visibility = View.INVISIBLE
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> {
                deleteAllData()
            }

            R.id.menu_priority_high -> {
                sortByHighPriority()
            }

            R.id.menu_priority_low -> {
                sortByLowPriority()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun sortByHighPriority() {
        mToDoViewModel.sortByHighPriority.observe(this, Observer {
            adapter.setData(it)
        })
    }

    private fun sortByLowPriority() {
        mToDoViewModel.sortByLowPriority.observe(this, Observer {
            adapter.setData(it)
        })
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

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                mToDoViewModel.deleteData(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                //Restore deleted item
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)

//                Toast.makeText(
//                    context,
//                    "Successfully deleted: '${deletedItem.title}'",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    internal fun restoreDeletedData(view: View, deletedItem: ToDoData, position: Int) {
        val snackBar = Snackbar.make(view, "Deleted '${deletedItem.title}'", Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo") {
            mToDoViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery = "%$query%"
        mToDoViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }
}