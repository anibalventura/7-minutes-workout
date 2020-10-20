@file:Suppress("BooleanLiteralArgument")

package com.anibalventura.t7minutesworkout.utils

import com.anibalventura.t7minutesworkout.R
import com.anibalventura.t7minutesworkout.data.ExerciseModel

object Constants {
    // Preferences.
    const val THEME = "theme"

    // Exercises
    fun getExercises(): MutableList<ExerciseModel> {
        return mutableListOf(
            ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false),
            ExerciseModel(2, "Wall Sit", R.drawable.ic_wall_sit, false, false),
            ExerciseModel(3, "Push Up", R.drawable.ic_push_up, false, false),
            ExerciseModel(4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false),
            ExerciseModel(5, "Step-Up onto Chair", R.drawable.ic_step_up_onto_chair, false, false),
            ExerciseModel(6, "Squat", R.drawable.ic_squat, false, false),
            ExerciseModel(
                7,
                "Triceps Dip On Chair",
                R.drawable.ic_triceps_dip_on_chair,
                false,
                false
            ),
            ExerciseModel(8, "Plank", R.drawable.ic_plank, false, false),
            ExerciseModel(
                9,
                "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place,
                false,
                false
            ),
            ExerciseModel(10, "Lunges", R.drawable.ic_lunge, false, false),
            ExerciseModel(
                11,
                "Push up and Rotation",
                R.drawable.ic_push_up_and_rotation,
                false,
                false
            ),
            ExerciseModel(12, "Side Plank", R.drawable.ic_side_plank, false, false)
        )
    }
}