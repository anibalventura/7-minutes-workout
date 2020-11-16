package com.anibalventura.t7minutesworkout.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anibalventura.t7minutesworkout.R
import com.anibalventura.t7minutesworkout.data.models.ExerciseModel
import kotlinx.android.synthetic.main.recyclerview_exercise_status.view.*

class ExerciseStatusAdapter(
    private val items: MutableList<ExerciseModel>, private val context: Context
) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_exercise_status, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exerciseModel: ExerciseModel = items[position]
        holder.tvItem.text = exerciseModel.getId().toString()

        when {
            exerciseModel.getIsSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(context, R.drawable.circular_item_accent_border)
                holder.tvItem.setTextColor(
                    ActivityCompat.getColor(context, R.color.secondaryTextColor)
                )
            }
            exerciseModel.getIsCompleted() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(context, R.drawable.circular_item_accent_bg)
                holder.tvItem.setTextColor(
                    ActivityCompat.getColor(context, R.color.primaryTextColor)
                )
            }
            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(context, R.drawable.circular_item_bg)
                holder.tvItem.setTextColor(
                    ActivityCompat.getColor(context, R.color.secondaryTextColor)
                )
            }
        }
    }

    override fun getItemCount(): Int = items.size
}