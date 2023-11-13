package com.example.fitnessapp.model

import com.example.fitnessapp.viewmodel.LoadState

interface Repository {
    suspend fun fetchTrainingInfo(): LoadState
}