package com.example.animelist.view

import com.example.animelist.model.AnimeResponse

// loading, error, success
// used hold states of the app
sealed class UIState {
    object Loading: UIState()
    class Error(val error: Exception): UIState()
    class Success<T>(val response: T): UIState()
}

// a group of related constants
enum class DaysOfTheWeek {
    Monday, // = 0
    Tuesday, // = 1
    Wednesday // = 2
}

// an enum class with superpowers
// a group of related classes/objects
sealed class WeekDays {
    class Monday(val number: Int): WeekDays()
    data class Tuesday(val flag: Boolean): WeekDays()
    object Wednesday: WeekDays()
}
