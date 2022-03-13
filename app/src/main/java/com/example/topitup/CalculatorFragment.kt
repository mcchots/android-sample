package com.example.topitup

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.topitup.databinding.CalculatorFragmentBinding
import com.example.topitup.viewmodels.CalculatorViewModel
import java.lang.Exception
import java.text.DecimalFormat

class CalculatorFragment : Fragment() {

    private var _binding: CalculatorFragmentBinding? = null

    private var firstValue: Double = Double.NaN
    private var secondValue: Double = 0.0
    private var operator: String = ""
    private val formatDecimal = DecimalFormat("#.##########")
    private var screenText: String = ""
    private var isDot: Boolean = false
    private var clearScreen: Boolean = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* val calculatorViewModel =
             ViewModelProvider(this).get(CalculatorViewModel::class.java)*/

        _binding = CalculatorFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button0.setOnClickListener(listener)
        binding.button1.setOnClickListener(listener)
        binding.button2.setOnClickListener(listener)
        binding.button3.setOnClickListener(listener)
        binding.button4.setOnClickListener(listener)
        binding.button5.setOnClickListener(listener)
        binding.button6.setOnClickListener(listener)
        binding.button7.setOnClickListener(listener)
        binding.button8.setOnClickListener(listener)
        binding.button9.setOnClickListener(listener)
        binding.buttonAC.setOnClickListener(listener)
        binding.buttonPlusMinus.setOnClickListener(listener)
        binding.buttonPercentage.setOnClickListener(listener)
        binding.buttonDivide.setOnClickListener(listener)
        binding.buttonMultiply.setOnClickListener(listener)
        binding.buttonMinus.setOnClickListener(listener)
        binding.buttonPlus.setOnClickListener(listener)
        binding.buttonDot.setOnClickListener(listener)
        binding.buttonEqual.setOnClickListener(listener)

    }

    private fun calculate() {
        if (!firstValue.isNaN() and !screenText.isNullOrEmpty()) {
            secondValue = screenText.toDouble()
            binding.editTextNumberDecimal.text = null
            when (operator) {
                "+" -> firstValue += secondValue
                "-" -> firstValue -= secondValue
                "*" -> firstValue *= secondValue
                "/" -> firstValue /= secondValue
            }
            screenText = formatDecimal.format(firstValue).toString()
        } else {
            try {
                firstValue = screenText.toDouble()
            } catch (e: Exception) {
                Log.d("Calculator", e.toString())
            }
        }
        isDot = false
    }

    private val listener = View.OnClickListener { view ->
        if (clearScreen) {
            screenText = ""
            clearScreen = false
        }
        when (view.id) {
            R.id.button1 -> {
                screenText += "1"
            }
            R.id.button2 -> {
                screenText += "2"
            }
            R.id.button3 -> {
                screenText += "3"
            }
            R.id.button4 -> {
                screenText += "4"
            }
            R.id.button5 -> {
                screenText += "5"
            }
            R.id.button6 -> {
                screenText += "6"
            }
            R.id.button7 -> {
                screenText += "7"
            }
            R.id.button8 -> {
                screenText += "8"
            }
            R.id.button9 -> {
                screenText += "9"
            }
            R.id.button0 -> {
                screenText += "0"
            }
            R.id.buttonDot -> {
                if (!isDot) {
                    screenText += "."
                }
                isDot = true
            }
            R.id.buttonPlus -> {   //"＋"
                calculate()
                operator = "+"
                clearScreen = true

            }
            R.id.buttonMinus -> {
                calculate()
                operator = "-"
                clearScreen = true
            }
            R.id.buttonMultiply -> {
                calculate()
                operator = "*"
                clearScreen = true
            }
            R.id.buttonDivide -> {
                calculate()
                operator = "/"
                clearScreen = true
            }
            R.id.buttonPercentage -> {
                // "％"
                if (!screenText.isNullOrEmpty()) {
                    screenText = formatDecimal.format((screenText.toDouble() / 100)).toString()
                }
            }
            R.id.buttonPlusMinus -> {
                // "+/-"
                if (!screenText.isNullOrEmpty()) {
                    screenText = formatDecimal.format((screenText.toDouble() * -1)).toString()
                }
            }
            R.id.buttonEqual -> {
                // "="
                calculate()
                screenText = formatDecimal.format(firstValue).toString()
                firstValue = Double.NaN
                clearScreen = false

            }
            R.id.buttonAC -> {
                screenText = ""
                secondValue = Double.NaN
                firstValue = Double.NaN
                isDot = false
            }
        }

        binding.editTextNumberDecimal.text = screenText
    }


}