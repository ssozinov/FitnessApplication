package com.example.fitnessapp.model

data class LessonEntity(
    val type: ResultType,
    val lesson: LessonDTO?,
    val header: String?
)

enum class ResultType {
    IsTraining,
    IsHeader
}