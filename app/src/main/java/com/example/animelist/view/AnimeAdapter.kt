package com.example.animelist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animelist.databinding.AnimeListItemBinding
import com.example.animelist.model.AnimeData

class AnimeAdapter(
    private val animeList: MutableList<AnimeData> = mutableListOf(),
    private val openAnimeDetails: (Int) -> Unit
    ): RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    fun setAnimeList(newList: List<AnimeData>) {
        animeList.clear()
        animeList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class AnimeViewHolder(
        private val binding: AnimeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: AnimeData) {
            binding.tvListName.text = data.node.title
            Glide.with(binding.ivListImage)
                .load(data.node.mainPicture.medium)
                .into(binding.ivListImage)

            binding.root.setOnClickListener {
                openAnimeDetails(data.node.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AnimeViewHolder(
            AnimeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.onBind(animeList[position])
    }

    override fun getItemCount() = animeList.size
}