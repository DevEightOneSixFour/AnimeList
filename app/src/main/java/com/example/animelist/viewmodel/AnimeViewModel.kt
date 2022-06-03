package com.example.animelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animelist.api.AnimeRepositoryImpl
import com.example.animelist.view.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeViewModel(
    private val repositoryImpl: AnimeRepositoryImpl
) : ViewModel() {

    private val _animeList = MutableLiveData<UIState>()
    val animeList: LiveData<UIState> get() = _animeList

    fun getAnimeList(q: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repositoryImpl.getAnimeList(q).collect() {
                _animeList.postValue(it)
            }
        }
    }
}