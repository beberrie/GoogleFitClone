package ph.edu.usc24104013.ui.journal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ph.edu.usc24104013.data.entity.ActivityEntity
import ph.edu.usc24104013.databinding.ItemJournalBinding

class JournalAdapter : ListAdapter<ActivityEntity, JournalAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemJournalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ActivityEntity) {
            binding.tvJournalDate.text     = item.date
            binding.tvJournalSteps.text    = "%,d steps".format(item.steps)
            binding.tvJournalCalories.text = "%.0f kcal".format(item.calories)
            binding.tvJournalDistance.text = "%.2f km".format(item.distanceKm)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJournalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ActivityEntity>() {
        override fun areItemsTheSame(a: ActivityEntity, b: ActivityEntity) = a.id == b.id
        override fun areContentsTheSame(a: ActivityEntity, b: ActivityEntity) = a == b
    }
}