package com.example.fitnessapp.model
import retrofit2.Response
import retrofit2.http.GET


interface TrainingInfoAPI {
  @GET("schedule/get_v3/?club_id=2")
   suspend fun getTrainingInfo(): TrainingInfoDTO
}