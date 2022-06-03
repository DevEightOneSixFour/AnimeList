package com.example.animelist.model

import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    val data: List<AnimeData>,
    val paging: AnimePaging
)

data class AnimePaging(
    val next: String
)

data class AnimeData(
    val node: AnimeNode
)

data class AnimeNode(
    val id: Int,
    val title: String,
    @SerializedName("main_picture")
    val mainPicture: AnimePicture
)

data class AnimePicture(
    val medium: String,
    val large: String
)
