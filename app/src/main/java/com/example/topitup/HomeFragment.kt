package com.example.topitup

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topitup.databinding.HomeFragmentBinding
import com.example.topitup.viewmodels.HomeViewModel
import com.example.topitup.viewmodels.HomeViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeFragment: Fragment() {

    private var _binding: HomeFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: HomeViewModel by activityViewModels {
        HomeViewModelFactory(
            (activity?.application as UserApplication).database.userDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // get name and open in detail screen
        // Not used here in this application. I was just trying it out
        /*val userAdapter = UserAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                it.name

            )
            Log.d("Data",it.name)
            view.findNavController().navigate(action)
        }*/
        val userAdapter = UserAdapter()
        recyclerView.adapter = userAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getAll().collect() {
                userAdapter.submitList(it)
            }
        }
        lifecycle.coroutineScope.launch {
        viewModel.getTopStats().collect {
            it.forEach() {
                Log.d("Database", it.toString())
                binding.numUsers.text = it.total_users.toString()
                binding.numPoints.text = it.total_points.toString()
                binding.numCards.text = it.total_cards.toString()
                binding.leadingUser.text = it.leader
            }
        }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment, menu)
    }
}