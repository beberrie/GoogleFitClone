package ph.edu.usc24104013.ui.browse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ph.edu.usc24104013.databinding.ItemExerciseBinding
import ph.edu.usc24104013.utils.WorkoutGenerator

class ExerciseAdapter :
    ListAdapter<WorkoutGenerator.WorkoutExerciseItem, ExerciseAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutGenerator.WorkoutExerciseItem) {
            binding.tvExerciseEmoji.text       = item.emoji
            binding.tvExerciseName.text        = item.name
            binding.tvExerciseDuration.text    = item.duration
            binding.tvExerciseDescription.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<WorkoutGenerator.WorkoutExerciseItem>() {
        override fun areItemsTheSame(
            a: WorkoutGenerator.WorkoutExerciseItem,
            b: WorkoutGenerator.WorkoutExerciseItem
        ) = a.name == b.name
        override fun areContentsTheSame(
            a: WorkoutGenerator.WorkoutExerciseItem,
            b: WorkoutGenerator.WorkoutExerciseItem
        ) = a == b
    }
}