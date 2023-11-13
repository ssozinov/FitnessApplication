package com.example.fitnessapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.fitnessapp.view.MainFragment
import dagger.Component


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun viewModelFactory(): ViewModelProvider.Factory
}