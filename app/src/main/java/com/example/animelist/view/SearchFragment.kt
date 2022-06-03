package com.example.animelist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.animelist.databinding.FragmentSearchBinding

class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.btnStartSearch.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionNavSearchToNavList(
                    binding.etSearch.text.toString() // CharSequence -> String
                )
            )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}