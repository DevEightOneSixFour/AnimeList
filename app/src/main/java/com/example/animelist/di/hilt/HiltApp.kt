package com.example.animelist.di.hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// tells the compiler that Hilt will handle creating the app
// do not forget to update the name of the app in the manifest
@HiltAndroidApp
class HiltApp: Application()