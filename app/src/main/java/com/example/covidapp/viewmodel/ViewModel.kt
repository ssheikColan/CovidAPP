package com.example.covidapp.viewmodel

import androidx.lifecycle.ViewModel

abstract class ViewModel : ViewModel() {
    abstract fun getData()
}