package com.example.fitnessapp.viewmodel

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.ListItemBinding
import com.example.fitnessapp.databinding.HeaderItemBinding
import com.example.fitnessapp.model.LessonEntity
import com.example.fitnessapp.model.ResultType

class TrainingInfoAdapter(private val getTrainerById: (String) -> String?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var trainingInfo: List<LessonEntity> = emptyList()

    companion object {
        private const val VIEW_TYPE_TRAINING = 1
        private const val VIEW_TYPE_HEADER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_TRAINING -> {
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                TrainingViewHolder(binding)
            }

            VIEW_TYPE_HEADER -> {
                val binding = HeaderItemBinding.inflate(layoutInflater, parent, false)
                HeaderViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (trainingInfo[position].type) {
            ResultType.IsTraining -> VIEW_TYPE_TRAINING
            ResultType.IsHeader -> VIEW_TYPE_HEADER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = trainingInfo[position]

        when (holder) {
            is TrainingViewHolder -> {
                val binding = holder.binding
                binding.startTime.text = item.lesson?.startTime ?: ""
                binding.lastTime.text = item.lesson?.endTime ?: ""
                binding.trening.text = item.lesson?.name ?: ""
                binding.hall.text = item.lesson?.place ?: ""
                binding.color.setBackgroundColor(Color.parseColor(item.lesson?.color ?: "#FFFFFF"))
                item.lesson?.coachId?.let {
                    if (it == "") {
                        binding.profile.setBackgroundResource(R.drawable.ic_people)
                    } else {
                        binding.profile.setBackgroundResource(R.drawable.ic_person)
                    }
                    binding.name.text = getTrainerById(it)
                }
            }

            is HeaderViewHolder -> {
                holder.binding.date.text = item.header
            }
        }
    }

    override fun getItemCount() = trainingInfo.size

    fun setData(data: List<LessonEntity>) {
        trainingInfo = data
        notifyDataSetChanged()
    }

    class TrainingViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
    class HeaderViewHolder(val binding: HeaderItemBinding) : RecyclerView.ViewHolder(binding.root)
}
