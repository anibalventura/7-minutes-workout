package com.anibalventura.t7minutesworkout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anibalventura.t7minutesworkout.adapters.HistoryAdapter
import com.anibalventura.t7minutesworkout.data.viewmodel.HistoryViewModel
import com.anibalventura.t7minutesworkout.databinding.FragmentHistoryBinding
import jp.wasabeef.recyclerview.animators.LandingAnimator

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel: HistoryViewModel by viewModels()

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()

        historyViewModel.getDatabase.observe(viewLifecycleOwner, { data ->
            adapter.setData(data)
        })

        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView = binding.historyRecyclerView
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        recyclerView.itemAnimator = LandingAnimator().apply {
            addDuration = 300 // Milliseconds
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}