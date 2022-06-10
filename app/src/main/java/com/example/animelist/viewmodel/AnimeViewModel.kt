package com.example.animelist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.api.AnimeRepository
import com.example.animelist.model.AnimeNode
import com.example.animelist.view.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

const val TAG = "AnimeViewModel"
@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    // Using viewModelScope with our exception handler
    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    // For logging errors of the coroutine
    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}", throwable)
        }
    }

    private val _animeList = MutableLiveData<UIState>()
    val animeList: LiveData<UIState> get() = _animeList

    private val _animeDetails = MutableLiveData<UIState>()
    val animeDetails: LiveData<UIState> get() = _animeDetails

    lateinit var currentAnime: AnimeNode

    fun getAnimeList(q: String, offset: Int) {
        viewModelSafeScope.launch(dispatcher) {
            repository.getAnimeList(q, offset).collect {
                _animeList.postValue(it)
            }
        }
    }

    fun getAnimeDetails(id: Int) {
        viewModelScope.launch {
            repository.getAnimeDetails(id).collect {
                _animeDetails.postValue(it)
            }
        }
    }

    /*
        Using these set functions to start the opening fragments in the loading state.
        This way they the api is only called when the fragment is first opened.
     */
    fun setLoadingState() { _animeList.value = UIState.Loading }

    fun setAnimeDetails(node: AnimeNode) {
        currentAnime = node
        _animeDetails.value = UIState.Loading
    }
}