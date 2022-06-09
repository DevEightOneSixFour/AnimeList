package com.example.animelist.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.animelist.R
import com.example.animelist.databinding.FragmentSearchBinding

class SearchFragment : ViewModelFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.btnStartSearch.setOnClickListener {
            checkUserInput()
        }
        return binding.root
    }

    private fun checkUserInput() {
        /*
            isBlank vs isEmpty
            isEmpty checks if the string is empty -> ""
            isBlank checks if the string is empty or contains white spaces -> "   "
         */
        if (binding.etSearch.text.isBlank()) {
            Toast.makeText(context, resources.getString(R.string.empty_text), Toast.LENGTH_LONG)
                .show()
        } else {
            /*
                ViewModel should only have to load data once
                We are setting the loading state in the Search fragment
                    so configuration changes do not reload the data in
                    the AnimeListFragment
             */
            viewModel.setLoadingState()
            Log.d("*****", "search: $viewModel ")
            // using the navController to open the AnimeListFragment
            //      with the argument of the EditText
            findNavController().navigate(
                SearchFragmentDirections.actionNavSearchToNavList(
                    binding.etSearch.text.toString().trim() // CharSequence -> String
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}