package com.yz.todoapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yz.todoapp.R
import com.yz.todoapp.data.models.Priority
import com.yz.todoapp.data.models.ToDoData
import com.yz.todoapp.data.viewmodel.SharedViewModel
import com.yz.todoapp.data.viewmodel.ToDoViewModel
import com.yz.todoapp.databinding.FragmentAddBinding
import com.yz.todoapp.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private var binding: FragmentUpdateBinding? = null
    private val viewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.etTitle?.setText(args.currentItem.title)
        binding?.etDescription?.setText(args.currentItem.description)
        binding?.spinnerPriorities?.setSelection(sharedViewModel.parsPriority(args.currentItem.priority))
        binding?.spinnerPriorities?.onItemSelectedListener = sharedViewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu ->
                updateData()
            R.id.delete_menu ->
                deleteData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateData() {

        //TODO Simulate data until get it from list
        val data = ToDoData(
            args.currentItem.id,
            binding?.etTitle?.text.toString(),
            sharedViewModel.parsePriority(binding?.spinnerPriorities?.selectedItem.toString()),
            binding?.etDescription?.text.toString()
        )

        viewModel.update(data)

        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun deleteData() {

        //TODO Simulate data until get it from list

        //Create alert dialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete ${binding?.etTitle?.text.toString()}")
        builder.setMessage("Are you sure you want to delete${binding?.etTitle?.text.toString()}")
        builder.setPositiveButton("Yes") { _, _ ->
            //Delete the data
            viewModel.deleteData(args.currentItem)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }

        builder.create().show()
    }
}