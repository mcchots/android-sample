package com.example.topitup.ui.calculator

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.topitup.R
import com.example.topitup.databinding.CalculatorFragmentBinding

class CalculatorFragment : Fragment() {

    private var _binding: CalculatorFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calculatorViewModel =
            ViewModelProvider(this).get(CalculatorViewModel::class.java)

        _binding = CalculatorFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textCalculator
        calculatorViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        setHasOptionsMenu(true)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.calculator_fragment, menu)
    }
}