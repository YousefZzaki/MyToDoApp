package com.yz.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yz.todoapp.R
import com.yz.todoapp.data.models.ToDoData
import com.yz.todoapp.data.viewmodel.SharedViewModel
import com.yz.todoapp.data.viewmodel.ToDoViewModel
import com.yz.todoapp.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    private val viewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set menu options
        setHasOptionsMenu(true)

//      //Set priority color
        binding.spinnerPriorities.onItemSelectedListener = sharedViewModel.listener
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "inflater.inflate(R.menu.add_fragment_menu, menu)",
            "com.yz.todoapp.R"
        )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_menu -> {
                insertToDoList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertToDoList() {
        val title = binding.etTitle.text.toString()
        val priority = binding.spinnerPriorities.selectedItem.toString()
        val description = binding.etDescription.text.toString()

        val validation = sharedViewModel.isDataValid(title, description)

        if (validation) {
            // Insert data to database
            val newList = ToDoData(
                0,
                title,
                sharedViewModel.parsePriority(priority),
                description
            )
            viewModel.insertData(newList)
            Toast.makeText(
                requireContext(),
                "Successfully add the todo list",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else
            Toast.makeText(
                requireContext(),
                "Please fill out all fields",
                Toast.LENGTH_SHORT
            ).show()
    }

}
