package com.anibalventura.t7minutesworkout.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anibalventura.t7minutesworkout.App
import com.anibalventura.t7minutesworkout.R
import com.anibalventura.t7minutesworkout.utils.Constants.METRICS_UNITS_VIEW
import com.anibalventura.t7minutesworkout.utils.Constants.US_UNITS_VIEW
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.properties.Delegates

class BmiViewModel(application: Application) : AndroidViewModel(application) {

    private val res = App.resourses!!

    private val _bmiValue = MutableLiveData<String>()
    val bmiValue: LiveData<String>
        get() = _bmiValue

    private val _bmiLabel = MutableLiveData<String>()
    val bmiLabel: LiveData<String>
        get() = _bmiLabel

    private val _bmiDescription = MutableLiveData<String>()
    val bmiDescription: LiveData<String>
        get() = _bmiDescription

    private var bmi by Delegates.notNull<Float>()

    init {
        _bmiValue.value = ""
        _bmiLabel.value = ""
        _bmiDescription.value = ""
    }

    fun calculateBmi(weight: Float, height: Float, units: String) {
        when (units) {
            METRICS_UNITS_VIEW -> bmi = weight / ((height / 100) * (height / 100))
            US_UNITS_VIEW -> bmi = 703 * (weight / ((height * 12) * (height * 12)))
        }

        _bmiValue.value = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        getBmiResult()
    }

    private fun getBmiResult() {
        when {
            bmi.compareTo(15f) <= 0 -> {
                _bmiLabel.value = res.getString(R.string.bmi_label_verey_underweight)
                _bmiDescription.value = res.getString(R.string.bmi_description_verey_underweight)
            }
            bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0 -> {
                _bmiLabel.value = res.getString(R.string.bmi_label_severely_underweight)
                _bmiDescription.value = res.getString(R.string.bmi_description_severely_underweight)
            }
            bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0 -> {
                _bmiLabel.value = res.getString(R.string.bmi_label_underweight)
                _bmiDescription.value = res.getString(R.string.bmi_description_underweight)
            }
            bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0 -> {
                _bmiLabel.value = res.getString(R.string.bmi_label_normal)
                _bmiDescription.value = res.getString(R.string.bmi_description_normal)
            }
            bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0 -> {
                _bmiLabel.value = res.getString(R.string.bmi_label_overweight)
                _bmiDescription.value = res.getString(R.string.bmi_description_overweight)
            }
            bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0 -> {
                _bmiLabel.value = res.getString(R.string.bmi_label_obese_1)
                _bmiDescription.value = res.getString(R.string.bmi_description_obese_1)
            }
            bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 -> {
                _bmiLabel.value = res.getString(R.string.bmi_label_obese_2)
                _bmiDescription.value = res.getString(R.string.bmi_description_obese_2)
            }
            else -> {
                _bmiLabel.value = res.getString(R.string.bmi_label_obese_3)
                _bmiDescription.value = res.getString(R.string.bmi_description_obese_3)
            }
        }
    }
}