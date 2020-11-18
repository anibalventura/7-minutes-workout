package com.anibalventura.t7minutesworkout.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

fun snackBarMsg(view: View, message: String) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackBar.show()
}

fun hideKeyboard(activity: Activity, view: View) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}