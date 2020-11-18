package com.anibalventura.t7minutesworkout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anibalventura.t7minutesworkout.R
import com.anibalventura.t7minutesworkout.data.viewmodel.BmiViewModel
import com.anibalventura.t7minutesworkout.databinding.FragmentBmiBinding
import com.anibalventura.t7minutesworkout.utils.Constants.METRICS_UNITS_VIEW
import com.anibalventura.t7minutesworkout.utils.Constants.US_UNITS_VIEW
import com.anibalventura.t7minutesworkout.utils.hideKeyboard
import com.anibalventura.t7minutesworkout.utils.snackBarMsg

class BmiFragment : Fragment() {

    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    private val bmiViewModel: BmiViewModel by viewModels()

    private var currentUnitsView: String = METRICS_UNITS_VIEW

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.bmiFragment = this
        binding.bmiViewModel = bmiViewModel

        checkActualView()

        return binding.root
    }

    /** ====================================== Units View ====================================== **/

    private fun checkActualView() {
        binding.rgUnits.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbMetricUnits -> changeUnitsView(METRICS_UNITS_VIEW)
                R.id.rbUsUnits -> changeUnitsView(US_UNITS_VIEW)
            }
        }
    }

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
        val weight: Float
        val height: Float

        when {
            validateUnits() -> {
                when (currentUnitsView) {
                    METRICS_UNITS_VIEW -> {
                        weight = binding.etMetricUnitWeight.text.toString().toFloat()
                        height = binding.etMetricUnitHeight.text.toString().toFloat()

                        bmiViewModel.calculateBmi(weight, height, METRICS_UNITS_VIEW)
                    }
                    US_UNITS_VIEW -> {
                        weight = binding.etUsUnitWeight.text.toString().toFloat()
                        height = binding.etUsUnitHeight.text.toString().toFloat()

                        bmiViewModel.calculateBmi(weight, height, US_UNITS_VIEW)
                    }
                }

                binding.clBMIResult.visibility = View.VISIBLE
                activity?.let { hideKeyboard(it, requireView()) }
            }
            else -> snackBarMsg(requireView(), getString(R.string.bmi_complete_fields))
        }
    }

    /** ====================================== Fragment exit/close ====================================== **/

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}