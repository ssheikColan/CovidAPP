package com.example.covidapp.application

import android.app.Application
import com.example.covidapp.common.utils.NetworkHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var networkHelper: NetworkHelper
}