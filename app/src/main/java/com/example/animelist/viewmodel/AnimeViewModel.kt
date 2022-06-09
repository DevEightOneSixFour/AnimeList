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

const val TAG = "*****"
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

    init {
        getAllAnimeData()
    }

    fun getAllAnimeData() {

    }
    fun getAnimeList(q: String) {
        Log.d(TAG, "getAnimeList: Starting network call")
        viewModelSafeScope.launch(dispatcher) {
            repository.getAnimeList(q).collect {
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

    fun setLoadingState() { _animeList.value = UIState.Loading }

    fun setAnimeDetails(node: AnimeNode) {
        _animeDetails.value = UIState.Loading
        currentAnime = node
    }
}