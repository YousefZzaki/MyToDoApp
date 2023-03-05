package com.yz.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yz.todoapp.R
import com.yz.todoapp.adapters.ListAdapter
import com.yz.todoapp.data.models.ToDoData
import com.yz.todoapp.data.viewmodel.SharedViewModel
import com.yz.todoapp.data.viewmodel.ToDoViewModel
import com.yz.todoapp.databinding.FragmentListBinding
import com.yz.todoapp.utils.SwipeToDelete
import com.yz.todoapp.utils.hideSoftKeyboard
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var binding: FragmentListBinding? = null
    private val viewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val listAdapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater)

        binding?.lifecycleOwner = this
        binding?.mSharedViewModel = sharedViewModel

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       //Setup recyclerview
        setupRecyclerview()

        //Set options menu
        setHasOptionsMenu(true)

        //Get any ui changing
        viewModel.getAllData.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isDatabaseEmpty(data)
            listAdapter.setData(data)
        }

        //Hide the keyboard
        hideSoftKeyboard(requireActivity())
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "inflater.inflate(R.menu.list_fragment_menu, menu)",
            "com.yz.todoapp.R"
        )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all ->
                deleteAllData()
            R.id.menu_priority_high -> viewModel.sortByHighest.observe(viewLifecycleOwner) {
                listAdapter.setData(it)
            }
            R.id.menu_priority_low -> viewModel.sortByLowest.observe(viewLifecycleOwner) {
                listAdapter.setData(it)
            }
        }
        return true
    }


    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = listAdapter.dataList[viewHolder.bindingAdapterPosition]
                viewModel.deleteData(deletedItem)
                listAdapter.notifyItemRemoved(viewHolder.bindingAdapterPosition)

                //Restore delete item
                restoreDeletedItem(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedItem(view: View, toDoData: ToDoData) {
        Snackbar.make(
            view,
            "Delete ${toDoData.title} ",
            Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            viewModel.insertData(toDoData)
        }.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchInDatabase(query)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchInDatabase(newText)
        }
        return true
    }

    private fun searchInDatabase(searchQuery: String){
        val searchQuery = "%$searchQuery%"
        viewModel.search(searchQuery).observe(viewLifecycleOwner) { list ->
            list.let {
                listAdapter.setData(it)
            }
        }
    }

    private fun setupRecyclerview(){
        val recyclerView = binding?.rvList
        recyclerView?.adapter = listAdapter
        recyclerView?.itemAnimator = SlideInUpAnimator()
        recyclerView?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        swipeToDelete(recyclerView!!)
    }

    private fun deleteAllData() {

        //Create alert dialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you delete everything from database.")
        builder.setPositiveButton("Yes") { _, _ ->
            //Delete the data
            viewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully delete all data", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }

        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}