package com.example.fitnessapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.model.LessonDTO
import com.example.fitnessapp.model.LessonEntity
import com.example.fitnessapp.model.Repository
import com.example.fitnessapp.model.ResultType
import com.example.fitnessapp.model.TrainerDTO
import com.example.fitnessapp.model.TrainingInfoDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _trainingInfoLiveData = MutableLiveData<LoadState>()
    private var trainersList = mutableListOf<TrainerDTO>()
    val trainingInfoLiveData: LiveData<LoadState> get() = _trainingInfoLiveData

    fun fetchTrainingInfo() {
        viewModelScope.launch {
            _trainingInfoLiveData.value = LoadState.Loading
            try {
                val result: LoadState = repository.fetchTrainingInfo()
                if (result is LoadState.Success<*>) {
                    saveTrainerData(result?.data as TrainingInfoDTO)
                    _trainingInfoLiveData.value = getResultEntity(result.data as TrainingInfoDTO)
                }
            } catch (e: Exception) {
                _trainingInfoLiveData.value = LoadState.Error(e)
            }
        }
    }

    fun getTrainerById(id: String): String? {
        val trainer = trainersList.find {
            it.id == id
        }
        return trainer?.name ?: null
    }

    private fun saveTrainerData(trainers: TrainingInfoDTO) {
        trainers.trainers?.forEach {
            trainersList.add(it ?: TrainerDTO())
        }
    }

    private fun getResultEntity(result: TrainingInfoDTO): LoadState {

        val list = result.lessons?.sortedByDescending {
            it?.formatedDate
        }
        val map = mutableMapOf<String, ArrayList<LessonDTO>>()
        list?.forEach {
            it?.let {
                if (map[it.dateForPrint] == null)
                    map[it.dateForPrint] = ArrayList()
                map[it.dateForPrint]?.add(it)
            }
        }
        val lessonList = ArrayList<LessonEntity>()
        map.forEach() { entry ->
            lessonList.add(LessonEntity(type = ResultType.IsHeader, null, entry.key))
            entry.value.mapTo(lessonList) {
                LessonEntity(type = ResultType.IsTraining, it, null)
            }
        }
        return LoadState.Success(lessonList)
    }
}