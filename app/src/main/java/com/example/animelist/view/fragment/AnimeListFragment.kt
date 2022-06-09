package com.example.animelist.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.animelist.databinding.FragmentAnimeListBinding
import com.example.animelist.model.AnimeNode
import com.example.animelist.model.AnimeResponse
import com.example.animelist.view.controller.AnimeListPageAdapter
import com.example.animelist.view.UIState

class AnimeListFragment: ViewModelFragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding: FragmentAnimeListBinding get() = _binding!!

    private lateinit var animeListPageAdapter: AnimeListPageAdapter

    // get the arguments of the fragment
    // navArgs() -> the arguments used to create the fragment
    private val args: AnimeListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeListBinding.inflate(layoutInflater)
        configureObserver()
        return binding.root
    }

    // to observe our ViewModel
    private fun configureObserver() {
        Log.d("*****", "animeList: $viewModel")
        viewModel.animeList.observe(viewLifecycleOwner) {
            when(it) {
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        animeListPageAdapter = AnimeListPageAdapter(openAnimeDetails = ::openAnimeDetails)
                        animeListPageAdapter.setAnimeList((it.response as AnimeResponse).data)
                        rvAnimeList.adapter = animeListPageAdapter
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvErrorText.text = it.error.message
                        tvErrorText.visibility = View.VISIBLE
                    }
                }
                is UIState.Loading -> { viewModel.getAnimeList(args.input) }
            }
        }
    }

    private fun openAnimeDetails(node: AnimeNode) {
        viewModel.setAnimeDetails(node)
        findNavController().navigate(
            AnimeListFragmentDirections.navListToNavDetails()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}