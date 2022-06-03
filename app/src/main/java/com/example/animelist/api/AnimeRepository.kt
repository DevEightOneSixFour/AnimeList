package com.example.animelist.api

import com.example.animelist.view.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

interface AnimeRepository {
    suspend fun getAnimeList(q: String): Flow<UIState>
}

class AnimeRepositoryImpl(private val crunchyRoll: CrunchyRoll) : AnimeRepository {
    override suspend fun getAnimeList(q: String): Flow<UIState> =
        flow {
            // start network call with loading state
            emit(UIState.Loading)

            // handle the network response
            try {
                // attempt some code
                val response = crunchyRoll.getAnimeList(q)
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
}