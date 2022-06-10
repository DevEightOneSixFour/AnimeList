package com.example.animelist.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.animelist.viewmodel.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class ViewModelFragment: Fragment() {
    // Manual
//    protected val viewModel: AnimeViewModel by lazy {
//        DI.provideViewModel(this)
//    }

    protected val viewModel: AnimeViewModel by activityViewModels()
}