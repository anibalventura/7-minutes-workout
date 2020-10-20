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