package com.example.animelist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.animelist.databinding.FragmentAnimeListBinding
import com.example.animelist.di.DI
import com.example.animelist.model.AnimeResponse
import com.example.animelist.viewmodel.AnimeViewModel

class AnimeListFragment: ViewModelFragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding: FragmentAnimeListBinding get() = _binding!!

    private lateinit var animeAdapter: AnimeAdapter

    // get the arguments of the fragment
    private val args: AnimeListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeListBinding.inflate(layoutInflater)
        animeAdapter = AnimeAdapter(openAnimeDetails = ::openAnimeDetails)
        return binding.root
    }

    // to observe our ViewModel
    fun configureObserver() {
        viewModel.animeList.observe(viewLifecycleOwner) {
            when(it) {
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        animeAdapter.setAnimeList((it.response as AnimeResponse).data)
                        rvAnimeList.adapter = animeAdapter
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvErrorText.text = it.error.message
                        tvErrorText.visibility = View.VISIBLE
                    }
                }
                else ->{}
            }
        }
    }

    private fun openAnimeDetails(id: Int) {
        findNavController().navigate(
            AnimeListFragmentDirections.navListToNavDetails(id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}