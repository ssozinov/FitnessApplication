package com.example.fitnessapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.fitnessapp.model.APIRepositoryImpl
import com.example.fitnessapp.model.Repository
import com.example.fitnessapp.viewmodel.MainFragmentViewModel
import com.example.fitnessapp.viewmodel.MainFragmentViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideRepository(): Repository {
        return APIRepositoryImpl()
    }
    @Provides
    fun provideViewModelFactory(repository: Repository): ViewModelProvider.Factory {
        return MainFragmentViewModelFactory(repository)
    }
}

