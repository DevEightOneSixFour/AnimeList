package com.example.animelist.view

import androidx.fragment.app.Fragment
import com.example.animelist.di.DI
import com.example.animelist.viewmodel.AnimeViewModel

open class ViewModelFragment: Fragment() {
    protected val viewModel: AnimeViewModel by lazy {
        DI.provideViewModel(this)
    }
}