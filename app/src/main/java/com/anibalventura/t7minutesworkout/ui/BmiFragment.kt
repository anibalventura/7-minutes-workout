package com.anibalventura.t7minutesworkout.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.anibalventura.t7minutesworkout.R
import com.anibalventura.t7minutesworkout.databinding.FragmentBmiBinding
import com.anibalventura.t7minutesworkout.utils.Constants.METRICS_UNITS_VIEW
import com.anibalventura.t7minutesworkout.utils.Constants.US_UNITS_VIEW
import com.anibalventura.t7minutesworkout.utils.snackBarMsg
import java.math.BigDecimal
import java.math.RoundingMode

class BmiFragment : Fragment() {

    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    private var currentUnitsView: String = METRICS_UNITS_VIEW

    private var bmiLabel: String = ""
    private var bmiDescription: String = ""
    private var bmiValue: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        binding.bmi = this

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbMetricUnits -> changeUnitsView(METRICS_UNITS_VIEW)
                R.id.rbUsUnits -> changeUnitsView(US_UNITS_VIEW)
            }
        }

        return binding.root
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    /** ====================================== Units View ====================================== **/

    private fun changeUnitsView(unit: String) {
        when (unit) {
            METRICS_UNITS_VIEW -> {
                currentUnitsView = METRICS_UNITS_VIEW

                binding.clMetricsUnits.visibility = View.VISIBLE
                binding.clUsUnits.visibility = View.INVISIBLE

                binding.etMetricUnitWeight.text!!.clear()
                binding.etMetricUnitHeight.text!!.clear()
            }
            US_UNITS_VIEW -> {
                currentUnitsView = US_UNITS_VIEW

                binding.clUsUnits.visibility = View.VISIBLE
                binding.clMetricsUnits.visibility = View.INVISIBLE

                binding.etUsUnitWeight.text!!.clear()
                binding.etUsUnitHeight.text!!.clear()
            }
        }

        binding.clBMIResult.visibility = View.INVISIBLE
    }

    /** ====================================== Calculate ====================================== **/

    private fun validateUnits(): Boolean {
        var isValid = true

        when (currentUnitsView) {
            METRICS_UNITS_VIEW -> {
                when {
                    binding.etMetricUnitWeight.text!!.isEmpty() -> isValid = false
                    binding.etMetricUnitHeight.text!!.isEmpty() -> isValid = false
                }
            }
            US_UNITS_VIEW -> {
                when {
                    binding.etUsUnitWeight.text!!.isEmpty() -> isValid = false
                    binding.etUsUnitHeight.text!!.isEmpty() -> isValid = false
                }
            }
        }

        return isValid
    }

    fun calculateUnits() {
        when {
            validateUnits() -> {
                when (currentUnitsView) {
                    METRICS_UNITS_VIEW -> {
                        val metricWeight: Float =
                            binding.etMetricUnitWeight.text.toString().toFloat()
                        val metricHeight: Float =
                            binding.etMetricUnitHeight.text.toString().toFloat() / 100
                        val bmi = metricWeight / (metricHeight * metricHeight)

                        displayBmiResult(bmi)
                    }
                    US_UNITS_VIEW -> {
                        val usWeight: Float = binding.etUsUnitWeight.text.toString().toFloat()
                        val usHeight = binding.etUsUnitHeight.text.toString().toFloat() * 12
                        val bmi = 703 * (usWeight / (usHeight * usHeight))

                        displayBmiResult(bmi)
                    }
                }
            }
            else -> snackBarMsg(requireView(), "Please complete fields.")
        }

        hideKeyboard()
    }

    private fun displayBmiResult(bmi: Float) {
        when {
            bmi.compareTo(15f) <= 0 -> {
                bmiLabel = "Very severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0 -> {
                bmiLabel = "Severely underweight"
                bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
            }
            bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0 -> {
                bmiLabel = "Underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0 -> {
                bmiLabel = "Normal"
                bmiDescription = "Congratulations! You are in a good shape!"
            }
            bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0 -> {
                bmiLabel = "Overweight"
                bmiDescription =
                    "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0 -> {
                bmiLabel = "Obese Class | (Moderately obese)"
                bmiDescription =
                    "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 -> {
                bmiLabel = "Obese Class || (Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
            else -> {
                bmiLabel = "Obese Class ||| (Very Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
        }

        bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription

        binding.clBMIResult.visibility = View.VISIBLE
    }

    /** ====================================== Fragment exit/close ====================================== **/

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}