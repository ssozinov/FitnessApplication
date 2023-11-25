package com.example.fitnessapp.model

import com.example.fitnessapp.viewmodel.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://olimpia.fitnesskit-admin.ru/"

class APIRepositoryImpl : Repository {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val fitnessAPI = retrofit.create(TrainingInfoAPI::class.java)

    override suspend fun getTrainingInfo(): LoadState {
        return try {
            val response = withContext(Dispatchers.IO) {
                fitnessAPI.getTrainingInfo()
            }
            if (response != null) {
                LoadState.Success(response)
            } else {
                LoadState.Error(Exception("No data"))
            }
        } catch (e: Exception) {
            LoadState.Error(e)
        }
    }
}

