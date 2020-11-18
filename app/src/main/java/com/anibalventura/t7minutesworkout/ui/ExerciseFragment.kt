package com.anibalventura.t7minutesworkout.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.anibalventura.t7minutesworkout.R
import com.anibalventura.t7minutesworkout.adapters.ExerciseStatusAdapter
import com.anibalventura.t7minutesworkout.data.models.ExerciseModel
import com.anibalventura.t7minutesworkout.data.models.Exercises.getExercises
import com.anibalventura.t7minutesworkout.databinding.FragmentExerciseBinding
import java.util.*

class ExerciseFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private var countDownTimer: CountDownTimer? = null
    private val countDownInterval: Long = 1000
    private var timerProgressBar: Long = 11000
    private var timerExercise: Long = 31000
    private var pauseOffset: Long = 0 // timerDuration - time left

    private var exerciseList: MutableList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseStatusAdapter: ExerciseStatusAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        tts = TextToSpeech(requireContext(), this)
        exerciseList = getExercises()

        startRestTimer(pauseOffset)
        setRestView()
        setupExerciseStatusRecyclerView()
        onBackPressed()

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
                    else -> findNavController().navigate(R.id.action_exerciseFragment_to_finishFragment)
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

    /** ====================================== Fragment exit/close ====================================== **/

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                        title(R.string.dialog_sure)
                        message(R.string.dialog_sure_msg)
                        negativeButton(R.string.dialog_negative)
                        positiveButton(R.string.dialog_confirmation) {
                            if (isEnabled) {
                                isEnabled = false
                                requireActivity().onBackPressed()
                            }
                        }
                    }
                }
            }
        )
    }

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