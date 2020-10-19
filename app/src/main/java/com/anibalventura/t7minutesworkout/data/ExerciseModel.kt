package com.anibalventura.t7minutesworkout.data

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

//fun getExercises(): MutableList<ExerciseModel> {
//    return mutableListOf(
//        ExerciseModel(R.drawable.ic_abdominal_crunch, "Abdominal Crunch"),
//        ExerciseModel(R.drawable.ic_high_knees_running_in_place, "High Knees Running in Place"),
//        ExerciseModel(R.drawable.ic_jumping_jacks, "Jumping Jacks"),
//        ExerciseModel(R.drawable.ic_lunge, "Lunge"),
//        ExerciseModel(R.drawable.ic_plank, "Plank"),
//        ExerciseModel(R.drawable.ic_push_up, "Push Up"),
//        ExerciseModel(R.drawable.ic_push_up_and_rotation, "Push Up and Rotation"),
//        ExerciseModel(R.drawable.ic_side_plank, "Side Plank"),
//        ExerciseModel(R.drawable.ic_squat, "Squat"),
//        ExerciseModel(R.drawable.ic_step_up_onto_chair, "Step Up onto Chair"),
//        ExerciseModel(R.drawable.ic_triceps_dip_on_chair, "Triceps Dip on Chair"),
//        ExerciseModel(R.drawable.ic_wall_sit, "Wall Sit")
//    )
//}
