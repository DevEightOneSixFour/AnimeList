package com.example.animelist.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.animelist.api.CrunchyRoll
import com.example.animelist.databinding.FragmentAnimeListBinding
import com.example.animelist.model.AnimeData
import com.example.animelist.model.AnimeNode
import com.example.animelist.model.AnimeResponse
import com.example.animelist.view.controller.AnimeListPageAdapter
import com.example.animelist.view.UIState

class AnimeListFragment : ViewModelFragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding: FragmentAnimeListBinding get() = _binding!!

    private lateinit var animeListPageAdapter: AnimeListPageAdapter

    // get the arguments of the fragment
    // navArgs() -> the arguments used to create the fragment
    private val args: AnimeListFragmentArgs by navArgs()
    private var currentOffset = 0
    private var shouldUpdateList = false

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
        viewModel.animeList.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Success<*> -> {
                    renderList(it.response as AnimeResponse)
                }
                is UIState.Error -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvErrorText.text = it.error.message
                        tvErrorText.visibility = View.VISIBLE
                    }
                }
                is UIState.Loading -> {
                    viewModel.getAnimeList(args.input, offset = currentOffset)
                }
            }
        }
    }

    private fun renderList(response: AnimeResponse) {
        binding.apply {
            pbLoading.visibility = View.GONE

            rvAnimeList.apply {
                // Setting the adapter once
                if (!shouldUpdateList) {
                    animeListPageAdapter =
                        AnimeListPageAdapter(openAnimeDetails = ::openAnimeDetails)
                    adapter = animeListPageAdapter
                }
                animeListPageAdapter.setAnimeList(
                    response.data,
                    shouldUpdateList
                )
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(
                        recyclerView: RecyclerView,
                        dx: Int,
                        dy: Int
                    ) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!rvAnimeList.canScrollVertically(1)) {
                            shouldUpdateList = true
                            viewModel.getAnimeList(
                                q = args.input,
                                currentOffset + CrunchyRoll.NUMBER_OF_ITEMS
                            )
                            Toast.makeText(
                                context,
                                "Loading more anime",
                                Toast.LENGTH_SHORT
                            ).show()
                            currentOffset += CrunchyRoll.NUMBER_OF_ITEMS
                        }
                    }
                })
            }
        }
    }

    private fun openAnimeDetails(node: AnimeNode) {
        viewModel.setAnimeDetails(node)
        shouldUpdateList = false
        findNavController().navigate(
            AnimeListFragmentDirections.navListToNavDetails()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}