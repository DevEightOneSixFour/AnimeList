package com.example.animelist.view.fragment

import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.example.animelist.R
import com.example.animelist.databinding.FragmentDetailsBinding
import com.example.animelist.model.AnimeData
import com.example.animelist.model.AnimeNode
import com.example.animelist.utils.GlideHelper.getUrlWithHeaders
import com.example.animelist.view.UIState
import com.example.animelist.view.controller.AnimeNodeAdapter

class DetailsFragment: ViewModelFragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    lateinit var nodeAdapter: AnimeNodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        configureObserver()
        return binding.root
    }

    private val animeNode: AnimeNode = viewModel.currentAnime

    private fun configureObserver() {
        viewModel.animeDetails.observe(viewLifecycleOwner) {
            when(it) {
                is UIState.Success<*> -> {
                    renderDetails(it.response as AnimeNode)
                }
                is UIState.Error -> {
                    binding.apply {
                        pbDetailsLoading.visibility = View.GONE
                        tvDetailsError.text = it.error.message
                        tvDetailsError.visibility = View.VISIBLE
                    }
                }
                is UIState.Loading -> {
                    loadingState()
                }
            }
        }
    }

    private fun renderDetails(node: AnimeNode) {
        viewModel.setAnimeDetails(node)

        Glide.with(binding.ivDetailImage)
            .load(getUrlWithHeaders(animeNode.mainPicture.large))
            .into(binding.ivDetailImage)

        binding.apply {
            pbDetailsLoading.visibility = View.GONE
            tvDetailsError.visibility = View.GONE

            tvDetailTitleText.apply {
                text = animeNode.title
                visibility = View.VISIBLE
            }
            tvDetailEpisodes.apply {
                text = resources.getString(
                    R.string.details_episode_number,
                    animeNode.numEpisodes.toString()
                )
                visibility = View.VISIBLE
            }
            ivDetailImage.visibility = View.VISIBLE

            nodeAdapter = AnimeNodeAdapter(reloadPage = ::reloadPage)
            nodeAdapter.setNodeList(node.recommendations ?: emptyList())
            rvDetailsRecommendations.adapter = nodeAdapter

        }
    }

    private fun loadingState() {
        binding.apply {
            pbDetailsLoading.visibility = View.VISIBLE
            tvDetailsError.visibility = View.GONE
            tvDetailTitleText.visibility = View.GONE
            tvDetailRecommendations.visibility = View.GONE
            tvDetailEpisodes.visibility = View.GONE
            ivDetailImage.visibility = View.GONE
        }
        viewModel.getAnimeDetails(animeNode.id)
    }

    private fun reloadPage(node: AnimeNode) {
        viewModel.setAnimeDetails(node)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}