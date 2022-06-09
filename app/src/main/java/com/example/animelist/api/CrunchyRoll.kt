package com.example.animelist.api

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.animelist.model.AnimeNode
import com.example.animelist.model.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CrunchyRoll {

    @GET(ANIME)
    suspend fun getAnimeList(
        @Query(Q) q: String,
        @Query(LIMIT) limit: Int = 40
    ): Response<AnimeResponse>

    @GET(DETAILS)
    suspend fun getAnimeDetails(
        @Path(ANIME_ID) animeId: Int,
        @Query(FIELDS) field: String = FIELD_STRINGS
    ): Response<AnimeNode>

//    @POST, @PUT, @DELETE, @HEAD

    companion object {
        /*
        full anime list url -> https://api.myanimelist.net/v2/anime?q=dragon&limit=40
        full details url -> https://api.myanimelist.net/v2/anime/6033?fields=id,title,main_picture,genres,recommendations
         */

        const val BASE_URL = "https://api.myanimelist.net/v2/"
        const val ANIME = "anime"
        const val ANIME_ID = "anime_id"
        const val DETAILS = "$ANIME/{$ANIME_ID}"
        const val FIELDS = "fields"
        const val Q = "q"
        const val LIMIT = "limit"
        const val FIELD_STRINGS = "id,title,main_picture,synopsis,genres,recommendations,num_episodes,related_anime"
        const val CLIENT_KEY = "X-MAL-CLIENT-ID"
        const val KEY_VALUE = "2013f33d2173183199acc146d05af19c"
    }
}