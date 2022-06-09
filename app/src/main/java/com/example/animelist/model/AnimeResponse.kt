package com.example.animelist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class AnimeResponse(
    val data: List<AnimeData>,
    val paging: Paging
)

data class Paging(
    val next: String
)

// Parcelable is needed to pass the data class as an argument in navigation
@Parcelize
data class AnimeData(
    val node: AnimeNode
): Parcelable

@Parcelize
data class AnimeNode(
    val id: Int,
    val title: String,
    val rating: String,
    @SerializedName("main_picture")
    val mainPicture: MainPicture,
    val synopsis: String,
    val genres: List<AnimeGenre?>? = emptyList(),
    val recommendations: List<AnimeData?>? = emptyList(),
    @SerializedName("num_episodes")
    val numEpisodes: Int,
    @SerializedName("related_anime")
    val relatedAnime: List<AnimeData?>? = emptyList()
): Parcelable

@Parcelize
data class AnimeGenre(
    val id: Int,
    val name: String
): Parcelable

@Parcelize
data class MainPicture(
    val medium: String,
    val large: String
): Parcelable
