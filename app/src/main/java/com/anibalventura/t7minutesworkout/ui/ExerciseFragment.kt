package com.anibalventura.t7minutesworkout.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anibalventura.t7minutesworkout.databinding.FragmentExerciseBinding

class ExerciseFragment : Fragment() {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    // Timer.
    private var countDownTimer: CountDownTimer? = null
    private val countDownInterval: Long = 1000
    private var timerDuration: Long = 0
    private var pauseOffset: Long = 0 // pauseOffset = timerDuration - time left

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)

        // Start timer on ProgressBar.
        startProgressBar(pauseOffset)

        return binding.root
    }

    /** ====================================== Timers ====================================== **/

    private fun stopTimer() {
        countDownTimer!!.cancel()
        pauseOffset = 0
    }

    private fun startProgressBar(pauseOffSetL: Long) {
        timerDuration = 11000

        countDownTimer = object : CountDownTimer(timerDuration - pauseOffSetL, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerDuration - millisUntilFinished

                binding.tvProgressBarTimer.text = "${millisUntilFinished / countDownInterval}"
                binding.progressBar.progress = (millisUntilFinished / countDownInterval).toInt()
            }

            override fun onFinish() {
                binding.clProgressBar.visibility = View.INVISIBLE
                stopTimer()

                binding.clExerciseTimer.visibility = View.VISIBLE
                startExerciseProgressBar(pauseOffset)
            }
        }.start()
    }

    private fun startExerciseProgressBar(pauseOffSetL: Long) {
        timerDuration = 31000

        countDownTimer = object : CountDownTimer(timerDuration - pauseOffSetL, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerDuration - millisUntilFinished

                binding.tvExerciseTimer.text = "${millisUntilFinished / countDownInterval}"
                binding.pbExercise.progress = (millisUntilFinished / countDownInterval).toInt()
            }

            override fun onFinish() {
                stopTimer()
            }
        }.start()
    }

    // Destroy all references of the fragment to avoid memory leak.
    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        _binding = null
    }
}