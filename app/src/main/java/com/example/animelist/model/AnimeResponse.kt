package com.example.animelist.model

import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    val data: List<AnimeData>,
    val paging: Paging
)

data class Paging(
    val next: String
)

// Parcelable is needed to pass the data class as an argument in navigation
// ^^ ended up not needing this, but if you want a reference for this use
//      refer to YuGiOhDeckBuilder
data class AnimeData(
    val node: AnimeNode? = null
)

data class AnimeNode(
    val id: Int,
    val title: String,
    val rating: String,
    @SerializedName("main_picture")
    val mainPicture: MainPicture? = null,
    val synopsis: String,
    val genres: List<AnimeGenre?>? = emptyList(),
    val recommendations: List<AnimeData?>? = emptyList(),
    @SerializedName("num_episodes")
    val numEpisodes: Int,
    @SerializedName("related_anime")
    val relatedAnime: List<AnimeData?>? = emptyList()
)

data class AnimeGenre(
    val id: Int,
    val name: String
)

data class MainPicture(
    val medium: String,
    val large: String
)
