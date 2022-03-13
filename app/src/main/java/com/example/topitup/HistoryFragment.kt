package com.example.topitup

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topitup.databinding.HistoryFragmentBinding
import com.example.topitup.viewmodels.HistoryViewModel
import com.example.topitup.viewmodels.HistoryViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryFragment() : Fragment() {

    private var _binding: HistoryFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: HistoryViewModel by activityViewModels {
        HistoryViewModelFactory(
            (activity?.application as UserApplication).database.historyDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)*/

        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        /*val textView: TextView = binding.textHistory
        historyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/


        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val historyAdapter = HistoryAdapter()

        recyclerView.adapter = historyAdapter

        fun showAll() {
            lifecycle.coroutineScope.launch {
                viewModel.getAll().collect {
                    historyAdapter.submitList(it)
                }
            }
        }
        showAll()
        //https://github.com/chankruze/DatePickerDialogFragment/blob/main/app/src/main/java/in/geekofia/example/demoapp/HomeFragment.kt

        binding.searchButton.setOnClickListener {// create new instance of DatePickerFragment
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            // we have to implement setFragmentResultListener
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
                    binding.textHistory.text = date.toString()
                    Log.d("Date", date.toString())

                    //TODO: Is this the right way to do this?
                    lifecycle.coroutineScope.launch {
                        viewModel.searchDate(date.toString()).collect() {
                            historyAdapter.submitList(it)
                        }
                    }
                }
            }

            // show
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        //TODO: Add another button to reset the search
        binding.resetButton.setOnClickListener {
               showAll()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_fragment, menu)
    }
}