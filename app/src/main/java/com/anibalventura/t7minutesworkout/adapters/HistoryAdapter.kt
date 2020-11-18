package com.anibalventura.t7minutesworkout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anibalventura.t7minutesworkout.data.models.HistoryModel
import com.anibalventura.t7minutesworkout.databinding.RecyclerviewHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    private var dataList = emptyList<HistoryModel>()

    class MyViewHolder(private val binding: RecyclerviewHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historyModel: HistoryModel) {
            binding.historyModel = historyModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewHistoryBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val historyModel = dataList[position]
        holder.bind(historyModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(historyData: List<HistoryModel>) {
        val historyDiffUtil = HistoryDiffUtil(dataList, historyData)
        val historyDiffUtilResult = DiffUtil.calculateDiff(historyDiffUtil)
        this.dataList = historyData
        historyDiffUtilResult.dispatchUpdatesTo(this)
    }
}

private class HistoryDiffUtil(
    private val oldList: List<HistoryModel>,
    private val newList: List<HistoryModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].date == newList[newItemPosition].date
                && oldList[oldItemPosition].time == newList[newItemPosition].time
    }
}