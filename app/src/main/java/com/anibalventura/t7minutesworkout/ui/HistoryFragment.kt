package com.anibalventura.t7minutesworkout.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.anibalventura.t7minutesworkout.R
import com.anibalventura.t7minutesworkout.adapters.HistoryAdapter
import com.anibalventura.t7minutesworkout.data.viewmodel.HistoryViewModel
import com.anibalventura.t7minutesworkout.databinding.FragmentHistoryBinding
import com.anibalventura.t7minutesworkout.utils.snackBarMsg
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
        binding.historyViewModel = historyViewModel

        historyViewModel.getDatabase.observe(viewLifecycleOwner, { data ->
            historyViewModel.checkIfHistoryIsEmpty(data)
            adapter.setData(data)
        })

        setupRecyclerView()
        setHasOptionsMenu(true)

        return binding.root
    }

    /** ======================================= RecyclerView ======================================= **/

    private fun setupRecyclerView() {
        recyclerView = binding.historyRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        recyclerView.itemAnimator = LandingAnimator().apply {
            addDuration = 300 // Milliseconds
        }
    }

    /** ======================================= Options Menu ======================================= **/

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_history_delete -> deleteHistory()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteHistory() {
        MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            icon(R.drawable.ic_delete)
            title(R.string.dialog_delete_history_title)
            message(R.string.dialog_delete_history_confirmation)
            positiveButton(R.string.dialog_confirmation) {
                historyViewModel.deleteDatabase()
                snackBarMsg(requireView(), getString(R.string.snack_history_deleted))
            }
            negativeButton(R.string.dialog_negative)
        }
    }

    /** ===================================== Fragment exit/close ===================================== **/

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}