package com.anibalventura.t7minutesworkout.data.models

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

    fun setId(id: Int) {
        this.id = id
    }

    /** ============ Name ============ **/
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    /** ============ Image ============ **/
    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
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
    fun getExercises(): MutableList<ExerciseModel> {
        return mutableListOf(
            ExerciseModel(
                1, "Jumping Jacks", R.drawable.ic_jumping_jacks,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                2, "Wall Sit", R.drawable.ic_wall_sit,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                3, "Push Up", R.drawable.ic_push_up,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                5, "Step-Up onto Chair", R.drawable.ic_step_up_onto_chair,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(6, "Squat", R.drawable.ic_squat, isCompleted = false, isSelected = false),
            ExerciseModel(
                7,
                "Triceps Dip On Chair",
                R.drawable.ic_triceps_dip_on_chair,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(8, "Plank", R.drawable.ic_plank, isCompleted = false, isSelected = false),
            ExerciseModel(
                9,
                "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                10, "Lunges", R.drawable.ic_lunge,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                11,
                "Push up and Rotation",
                R.drawable.ic_push_up_and_rotation,
                isCompleted = false,
                isSelected = false
            ),
            ExerciseModel(
                12, "Side Plank", R.drawable.ic_side_plank,
                isCompleted = false,
                isSelected = false
            )
        )
    }
}