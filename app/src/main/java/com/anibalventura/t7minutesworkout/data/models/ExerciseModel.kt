package com.anibalventura.t7minutesworkout.data.models

import com.anibalventura.t7minutesworkout.App
import com.anibalventura.t7minutesworkout.R

data class ExerciseModel(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean
) {
    /** ============ ID ============ **/
    fun getId(): Int {
        return id
    }

    /** ============ Name ============ **/
    fun getName(): String {
        return name
    }

    /** ============ Image ============ **/
    fun getImage(): Int {
        return image
    }

    /** ============ Is Completed ============ **/
    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    /** ============ Is Selected ============ **/
    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}

object Exercises {
    private val res = App.resourses!!

    fun getExercises(): MutableList<ExerciseModel> {
        return mutableListOf(
            ExerciseModel(
                1, res.getString(R.string.exercise_jumping_jacks),
                R.drawable.ic_jumping_jacks,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                2, res.getString(R.string.exercise_wall_sit),
                R.drawable.ic_wall_sit,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                3, res.getString(R.string.exercise_push_up),
                R.drawable.ic_push_up,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                4, res.getString(R.string.exercise_abdominal_crunch),
                R.drawable.ic_abdominal_crunch,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                5, res.getString(R.string.exercise_step_up_chair),
                R.drawable.ic_step_up_onto_chair,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                6, res.getString(R.string.exercise_squat),
                R.drawable.ic_squat,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                7, res.getString(R.string.exercise_triceps_dip_chair),
                R.drawable.ic_triceps_dip_on_chair,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                8, res.getString(R.string.exercise_plank),
                R.drawable.ic_plank,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                9, res.getString(R.string.exercise_high_knees_running),
                R.drawable.ic_high_knees_running_in_place,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                10, res.getString(R.string.exercise_lunges),
                R.drawable.ic_lunge,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                11, res.getString(R.string.exercise_push_up_rotation),
                R.drawable.ic_push_up_and_rotation,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                12, res.getString(R.string.exercise_side_plank),
                R.drawable.ic_side_plank,
                isCompleted = false,
                isSelected = false
            )
        )
    }
}