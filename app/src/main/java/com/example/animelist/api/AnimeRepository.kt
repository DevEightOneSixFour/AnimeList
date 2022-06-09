package com.example.animelist.api

import android.util.Log
import com.example.animelist.view.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.Exception

interface AnimeRepository {
    suspend fun getAnimeList(q: String): Flow<UIState>
    suspend fun getAnimeDetails(id: Int): Flow<UIState>
}

class AnimeRepositoryImpl @Inject constructor(private val crunchyRoll: CrunchyRoll) : AnimeRepository {
    override suspend fun getAnimeList(q: String): Flow<UIState> =
        flow {
            // handle the network response
            try {
                // attempt some code
                val response = crunchyRoll.getAnimeList(q = q)
                Log.d("*****", "getAnimeList: $response")
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        UIState.Success(it)
                    } ?: throw Exception("Null Response"))
                } else {
                    throw Exception("Failed network call")
                }
            } catch (e: Exception) {
                // catch the errors and run this block instead
                emit(UIState.Error(e))
            }
        }

    override suspend fun getAnimeDetails(id: Int) =
        flow {
            emit(UIState.Loading)

            try {
                val response = crunchyRoll.getAnimeDetails(animeId = id)
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        Log.d("*****", "getAnimeDetails: ${response.body()}")
                        UIState.Success(it)
                    } ?: throw Exception("NullResponse"))
                } else throw Exception("Failed network call")
            } catch (e: Exception) {
                emit(UIState.Error(e))
            }
        }
}