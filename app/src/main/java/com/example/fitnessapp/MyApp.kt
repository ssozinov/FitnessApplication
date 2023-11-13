package com.example.fitnessapp

import android.app.Application
import com.example.fitnessapp.di.AppComponent
import com.example.fitnessapp.di.AppModule
import com.example.fitnessapp.di.DaggerAppComponent

class MyApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
    }
}