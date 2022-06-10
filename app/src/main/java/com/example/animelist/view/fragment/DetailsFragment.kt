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
                // only calls when the page is first entered
                //      or when reloading the page
                is UIState.Loading -> { loadingState() }
            }
        }
    }

    private fun renderDetails(node: AnimeNode) {

        Glide.with(binding.ivDetailImage)
            .load(getUrlWithHeaders(node.mainPicture?.large))
            .into(binding.ivDetailImage)

        binding.apply {
            pbDetailsLoading.visibility = View.GONE
            tvDetailsError.visibility = View.GONE

            tvDetailTitleText.apply {
                text = node.title
                visibility = View.VISIBLE
            }
            tvDetailEpisodes.apply {
                text = resources.getString(
                    R.string.details_episode_number,
                    node.numEpisodes.toString()
                )
                visibility = View.VISIBLE
            }
            tvDetailRecommendations.visibility = View.VISIBLE
            ivDetailImage.visibility = View.VISIBLE

            nodeAdapter = AnimeNodeAdapter(reloadPage = ::reloadPage)
            nodeAdapter.setNodeList(node.recommendations)
            rvDetailsRecommendations.adapter = nodeAdapter
            rvDetailsRecommendations.visibility = View.VISIBLE
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
            rvDetailsRecommendations.visibility = View.GONE
        }
        // api call details starts here
        viewModel.getAnimeDetails(viewModel.currentAnime.id)
    }

    private fun reloadPage(data: AnimeData) {
        // changes the currentAnime in the ViewModel
        //  and sets UIState to loading which reloads the page
        //  with new data
        viewModel.setAnimeDetails(data.node!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}