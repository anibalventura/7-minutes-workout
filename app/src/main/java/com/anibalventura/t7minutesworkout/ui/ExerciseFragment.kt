package com.anibalventura.t7minutesworkout.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anibalventura.t7minutesworkout.R
import com.anibalventura.t7minutesworkout.adapters.ExerciseStatusAdapter
import com.anibalventura.t7minutesworkout.data.ExerciseModel
import com.anibalventura.t7minutesworkout.data.Exercises.getExercises
import com.anibalventura.t7minutesworkout.databinding.FragmentExerciseBinding
import com.anibalventura.t7minutesworkout.utils.snackBarMsg
import java.util.*

class ExerciseFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private var countDownTimer: CountDownTimer? = null
    private val countDownInterval: Long = 1000
    private var timerProgressBar: Long = 3000
    private var timerExercise: Long = 5000
    private var pauseOffset: Long = 0 // pauseOffset = timerDuration - time left

    private var exerciseList: MutableList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseStatusAdapter: ExerciseStatusAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        tts = TextToSpeech(requireContext(), this)
        exerciseList = getExercises()

        startRestTimer(pauseOffset)
        setRestView()
        setupExerciseStatusRecyclerView()

        return binding.root
    }

    /** ====================================== TextToSpeech ====================================== **/

    override fun onInit(status: Int) {
        when (status) {
            TextToSpeech.SUCCESS -> tts!!.language = Locale.US
            TextToSpeech.LANG_MISSING_DATA -> Log.e("TTS", "Language missing!")
            TextToSpeech.LANG_NOT_SUPPORTED -> Log.e("TTS", "Language not supported!")
            else -> Log.e("TTS", "Initialization failed.")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    /** ====================================== RestView ====================================== **/

    private fun startRestTimer(pauseOffSetL: Long) {
        countDownTimer =
            object : CountDownTimer(timerProgressBar - pauseOffSetL, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    pauseOffset = timerProgressBar - millisUntilFinished

                    binding.tvRestTimer.text = "${millisUntilFinished / countDownInterval}"
                    binding.pbRest.progress = (millisUntilFinished / countDownInterval).toInt()
                }

                override fun onFinish() {
                    setExerciseView()
                    exerciseList!![currentExercisePosition].setIsSelected(true)
                    exerciseStatusAdapter!!.notifyDataSetChanged()
                }
            }.start()
    }

    private fun setRestView() {
        binding.clRestView.visibility = View.VISIBLE
        binding.clExerciseView.visibility = View.INVISIBLE

        try {
            player = MediaPlayer.create(requireContext(), R.raw.start)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.tvUpcomingExercise.text = exerciseList!![currentExercisePosition + 1].getName()
    }

    /** ====================================== ExerciseView ====================================== **/

    private fun startExerciseTimer(pauseOffSetL: Long) {
        countDownTimer = object : CountDownTimer(timerExercise - pauseOffSetL, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerExercise - millisUntilFinished

                binding.tvExerciseTimer.text = "${millisUntilFinished / countDownInterval}"
                binding.pbExercise.progress = (millisUntilFinished / countDownInterval).toInt()
            }

            override fun onFinish() {
                when {
                    currentExercisePosition < exerciseList!!.size - 1 -> {
                        stopTimer()
                        startRestTimer(pauseOffset)
                        setRestView()
                        exerciseList!![currentExercisePosition].setIsSelected(false)
                        exerciseList!![currentExercisePosition].setIsCompleted(true)
                        exerciseStatusAdapter!!.notifyDataSetChanged()
                    }
                    else -> snackBarMsg(
                        requireView(), getString(R.string.snack_bar_congratulations)
                    )
                }
            }
        }.start()
    }

    private fun setExerciseView() {
        stopTimer()

        binding.clRestView.visibility = View.INVISIBLE
        binding.clExerciseView.visibility = View.VISIBLE

        speakOut(exerciseList!![currentExercisePosition + 1].getName())
        startExerciseTimer(pauseOffset)

        currentExercisePosition++
        binding.ivExercise.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    private fun setupExerciseStatusRecyclerView() {
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        exerciseStatusAdapter = ExerciseStatusAdapter(exerciseList!!, requireContext())
        binding.rvExerciseStatus.adapter = exerciseStatusAdapter
    }

    /** ====================================== App exit/close ====================================== **/

    override fun onDestroy() {
        stopTimer()
        stopTTS()
        stopPLayer()
        _binding = null

        super.onDestroy()
    }

    private fun stopTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            pauseOffset = 0
        }
    }

    private fun stopTTS() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }

    private fun stopPLayer() {
        if (player != null) {
            player!!.stop()
        }
    }
}