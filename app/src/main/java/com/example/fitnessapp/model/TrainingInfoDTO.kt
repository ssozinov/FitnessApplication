package com.example.fitnessapp.model


import android.icu.text.SimpleDateFormat
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


data class TrainingInfoDTO(
    @SerializedName("lessons")
    val lessons: List<LessonDTO?>?,
    @SerializedName("option")
    val option: OptionDTO?,
    @SerializedName("tabs")
    val tabs: List<TabDTO?>?,
    @SerializedName("trainers")
    val trainers: List<TrainerDTO?>?
)


data class TrainerDTO(
    val description: String?=null,
    @SerializedName("full_name")
    val fullName: String?=null,
    @SerializedName("id")
    val id: String?=null,
    val imageUrl: String?=null,
    val imageUrlMedium: String?=null,
    val imageUrlSmall: String?=null,
    @SerializedName("last_name")
    val lastName: String?=null,
    @SerializedName("name")
    val name: String?=null,
    val position: String?=null
)

data class TabDTO(
    val id: Int?,
    val name: String?
)

data class OptionDTO(
    val clubName: String?,
    val linkAndroid: String?,
    val linkIos: String?,
    val primaryColor: String?,
    val secondaryColor: String?
)

data class LessonDTO(
    @SerializedName("appointment_id")
    val appointmentId: String?,
    val availableSlots: Int?,
    val clientRecorded: Boolean?,
    @SerializedName("coach_id")
    val coachId: String?,
    @SerializedName("color")
    val color: String?,
    val commercial: Boolean?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("endTime")
    val endTime: String?,
    val isCancelled: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("place")
    val place: String?,
    @SerializedName("service_id")
    val serviceId: String?,
    @SerializedName("startTime")
    val startTime: String?,
    val tab: String?,
    val tabId: Int?
){
    val formatedDate: Date
       get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)

    val dateForPrint: String
        get() = SimpleDateFormat("EEEE, dd MMMM ", Locale("ru")).format(formatedDate)

}