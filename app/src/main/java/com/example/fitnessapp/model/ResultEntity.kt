package com.example.fitnessapp.model

interface Item
data class LessonItem(val lesson: LessonDTO) : Item

data class HeaderItem(val header: String) : Item
