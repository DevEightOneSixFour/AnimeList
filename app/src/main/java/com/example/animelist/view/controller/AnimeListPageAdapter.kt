package com.example.animelist.view.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animelist.databinding.AnimeListItemBinding
import com.example.animelist.databinding.BottomOfAnimeListBinding
import com.example.animelist.model.AnimeData
import com.example.animelist.model.AnimeNode
import com.example.animelist.utils.GlideHelper.getUrlWithHeaders

// Todo refactor to ListAdapter with DiffUtils
class AnimeListPageAdapter(
    private val animeList: MutableList<AnimeData?> = mutableListOf(),
    private val openAnimeDetails: (AnimeNode) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM = 0
        const val LOADING = 1
    }

    fun setAnimeList(newList: List<AnimeData>) {
        if (animeList.isNotEmpty()) animeList.removeLast()
        animeList.addAll(newList)
        animeList.add(null) // for our loading bar
        notifyDataSetChanged()
    }

//    private class AnimeListCallback(
//        private val oldList: List<AnimeData>,
//        private val newList: List<AnimeData>
//    ) : DiffUtil.Callback() {
//        override fun getOldListSize(): Int = oldList.size
//        override fun getNewListSize(): Int = newList.size
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
//            oldList[oldItemPosition].node?.id == newList[newItemPosition].node?.id
//
//        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
//            oldList == newList
//    }

//    private class AnimeListCallback: DiffUtil.ItemCallback<AnimeData>() {
//        override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean =
//            oldItem.node?.id == newItem.node?.id
//
//        override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean =
//            oldItem == newItem
//    }

    inner class AnimeViewHolder(
        private val binding: AnimeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: AnimeData) {
            binding.tvListName.text = data.node?.title
            Glide.with(binding.ivListImage)
                .load(getUrlWithHeaders(data.node?.mainPicture?.medium))
                .into(binding.ivListImage)

            binding.btnListDetails.setOnClickListener {
                openAnimeDetails(data.node!!)
            }
        }
    }

    inner class LoadingViewHolder(
        private val binding: BottomOfAnimeListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onShowLoading() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                AnimeViewHolder(
                    AnimeListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                LoadingViewHolder(
                    BottomOfAnimeListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AnimeViewHolder -> holder.onBind(animeList[position]!!)
            is LoadingViewHolder -> {
                holder.onShowLoading()
            }
        }
    }

    override fun getItemCount() = animeList.size

    override fun getItemViewType(position: Int): Int {
        return if (animeList[position] == null) LOADING else ITEM
    }
}