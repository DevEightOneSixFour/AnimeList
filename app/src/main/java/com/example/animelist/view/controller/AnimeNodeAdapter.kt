package com.example.animelist.view.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animelist.databinding.AnimeNodeItemBinding
import com.example.animelist.model.AnimeData
import com.example.animelist.model.AnimeNode
import com.example.animelist.utils.GlideHelper.getUrlWithHeaders

// adapter for the recommendations/related anime
class AnimeNodeAdapter(
    private val nodeList: MutableList<AnimeData> = mutableListOf(),
    private val reloadPage: (AnimeNode) -> Unit
): RecyclerView.Adapter<AnimeNodeAdapter.AnimeNodeViewHolder>() {

    inner class AnimeNodeViewHolder(
        private val binding: AnimeNodeItemBinding
        ): RecyclerView.ViewHolder(binding.root) {
            fun onBind(data: AnimeData) {
                Glide.with(binding.ivNodeImage)
                    .load(getUrlWithHeaders(data.node.mainPicture.medium))
                    .into(binding.ivNodeImage)
                binding.tvNodeTitle.text = data.node.title

                binding.root.setOnClickListener {
                    reloadPage(data.node)
                }
            }
        }

    fun setNodeList(newList: List<AnimeData?>?) {
        if (nodeList.isEmpty()) {
            nodeList.clear()
            notifyItemRangeChanged(0, itemCount)
        } else {

            nodeList.clear()
            nodeList.addAll(newList as Collection<AnimeData>)
            notifyItemRangeChanged(0, itemCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeNodeViewHolder {
        return AnimeNodeViewHolder(
            AnimeNodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AnimeNodeViewHolder, position: Int) {
        holder.onBind(nodeList[position])
    }

    override fun getItemCount() = nodeList.size
}