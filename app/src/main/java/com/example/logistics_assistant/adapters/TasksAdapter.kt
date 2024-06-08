package com.example.logistics_assistant.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.logistics_assistant.R
import com.example.logistics_assistant.database.TasksModel
import com.example.logistics_assistant.databinding.ItemContainerTaskBinding

class TasksAdapter(private val listener: OnItemClickListener) :
    ListAdapter<TasksModel, TasksAdapter.Holder>(Comparator()) {
    inner class Holder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val binding = ItemContainerTaskBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: TasksModel) = with(binding) {
            tvTask.text = item.taskNum
            tvPrice.text = item.price
            tvStatus.text = item.status
            tvDateOnly.text = item.dateTask
            tvTimeOnly.text = item.timeTask
            tvPlaceA.text = item.placeA
            tvDatePlaceA.text = item.datePlaceA
            tvTimePlaceA.text = item.timePlaceA
            tvPlaceB.text = item.placeB
            tvDatePlaceB.text = item.datePlaceB
            tvTimePlaceB.text = item.timePlaceB
            when (item.status) {
                "Новое" -> tvStatus.setTextColor(Color.parseColor("#59C617"))
                "Запланированные" -> tvStatus.setTextColor(Color.parseColor("#308ADD"))
                "В процессе" -> tvStatus.setTextColor(Color.parseColor("#C649F2"))
                "Проверка" -> tvStatus.setTextColor(Color.parseColor("#FAB020"))
                "Ожидает оплаты" -> tvStatus.setTextColor(Color.parseColor("#FAB020"))
                "В работе" -> tvStatus.setTextColor(Color.parseColor("#4682B4"))
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val task = getItem(position)
                listener.onItemClick(task)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<TasksModel>() {
        override fun areItemsTheSame(oldItem: TasksModel, newItem: TasksModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TasksModel, newItem: TasksModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_container_task, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClick(task: TasksModel)
    }

}