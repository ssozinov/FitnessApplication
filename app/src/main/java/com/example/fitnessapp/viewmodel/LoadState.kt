package com.example.fitnessapp.viewmodel

sealed class LoadState {

    data class Success<T>(val data: T) : LoadState()
    data class Error(val error: Throwable) : LoadState()
    data object Loading : LoadState()
}