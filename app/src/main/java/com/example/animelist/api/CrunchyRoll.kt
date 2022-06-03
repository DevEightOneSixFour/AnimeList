package com.example.animelist.api

import com.example.animelist.model.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Query

interface CrunchyRoll {

    @GET(ANIME)
    suspend fun getAnimeList(
        @Query(Q) q: String,
        @Query(LIMIT) limit: Int = 40
    ): Response<AnimeResponse>

//    @POST, @PUT, @DELETE, @HEAD



    companion object {
        // https://api.myanimelist.net/v2/anime?q=dragon&limit=40
        const val BASE_URL = "https://api.myanimelist.net/v2/"
        const val ANIME = "anime"
        const val Q = "q"
        const val LIMIT = "limit"

        const val CLIENT_KEY = "X-MAL-CLIENT-ID"
        const val KEY_VALUE = "3c3f84c0e414498d9240c05f5042206a"
    }
}