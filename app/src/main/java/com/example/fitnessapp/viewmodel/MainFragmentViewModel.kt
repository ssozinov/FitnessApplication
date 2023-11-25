package com.example.fitnessapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.model.HeaderItem
import com.example.fitnessapp.model.Item
import com.example.fitnessapp.model.LessonItem
import com.example.fitnessapp.model.Repository
import com.example.fitnessapp.model.TrainerDTO
import com.example.fitnessapp.model.TrainingInfoDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _trainingInfoLiveData = MutableLiveData<LoadState>()
    private var trainersList = mutableListOf<TrainerDTO>()
    val trainingInfoLiveData: LiveData<LoadState> get() = _trainingInfoLiveData

    init {
        fetchTrainingInfo()
    }

    private fun fetchTrainingInfo() {
        viewModelScope.launch {
            _trainingInfoLiveData.value = LoadState.Loading
            try {
                val result: LoadState = repository.getTrainingInfo()
                if (result is LoadState.Success<*>) {
                    saveTrainerData(result.data as TrainingInfoDTO)
                    _trainingInfoLiveData.value = getResultEntity(result.data)
                }
            } catch (e: Exception) {
                _trainingInfoLiveData.value = LoadState.Error(e)
            }
        }
    }

    private fun saveTrainerData(trainers: TrainingInfoDTO) {
        trainers.trainers?.forEach {
            trainersList.add(it ?: TrainerDTO())
        }
    }

    private fun getResultEntity(result: TrainingInfoDTO): LoadState {
        val lessonList = result.lessons
            ?.filterNotNull()
            ?.sortedBy { it.formatedDate }
            ?.groupBy { it.dateForPrint }
            ?.flatMap { (date, lessons) ->
                mutableListOf<Item>().apply {
                    add(HeaderItem(date))
                    addAll(lessons.map { lessonDto ->
                        val trainer = result.trainers?.find { it?.id == lessonDto.coachId }
                        val resultLessonDTO = lessonDto.copy(lastName = trainer?.fullName)
                        LessonItem(resultLessonDTO)
                    })
                }
            }
            ?: emptyList()

        return LoadState.Success(lessonList)
    }
}