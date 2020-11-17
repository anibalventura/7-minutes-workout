package com.anibalventura.t7minutesworkout.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

class BindingAdapters {
    companion object {

        @JvmStatic
        @BindingAdapter("android:emptyDatabase")
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }
    }
}