package com.example.topitup

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.topitup.databinding.CalculatorFragmentBinding
import com.example.topitup.viewmodels.CalculatorViewModel

class CalculatorFragment : Fragment() {

    private var _binding: CalculatorFragmentBinding? = null

    private var screenValue: Long = 0
    private var previousValue: Long = 0
    private var operator: String = ""
    var screenText: String = ""

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

        setHasOptionsMenu(true)
        return root
    }

/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ////////////////////////////////////////////////////////////////////////////////////
        var isNewOp=true
        var dot=false
        val etShowNumber = binding.editTextNumberDecimal
        fun buNumberEvent(view: View)
        {
            if(isNewOp)
            {
                etShowNumber.setText("")
            }
            isNewOp=false
            val buSelect= view as Button
            var buClickValue:String=etShowNumber.text.toString()
            when(buSelect.id)
            {
                button0.id->
                {
                    buClickValue+="0"
                }
                button1.id->
                {
                    buClickValue+="1"
                }
                button2.id->
                {
                    buClickValue+="2"
                }
                button3.id->
                {
                    buClickValue+="3"
                }
                button4.id->
                {
                    buClickValue+="4"
                }
                button5.id->
                {
                    buClickValue+="5"
                }
                button6.id->
                {
                    buClickValue+="6"
                }
                button7.id->
                {
                    buClickValue+="7"
                }
                button8.id->
                {
                    buClickValue+="8"
                }
                button9.id->
                {
                    buClickValue+="9"
                }
                buttonDot.id->
                {
                    if(dot==false)
                    {
                        buClickValue += "."
                    }
                    dot=true
                }
                buttonPlusMinus.id->
                {
                    buClickValue="-" + buClickValue
                }
            }
            etShowNumber.setText(buClickValue)
        }
        var op="X"
        var oldNumber=""

        fun buOpEvent(view: View)
        {
            val buSelect= view as Button
            when(buSelect.id)
            {
                buttonMuliply.id->
                {
                    op="X"
                }
                buttonDivide.id->
                {
                    op="÷"
                }
                buttonSubtract.id->
                {
                    op="-"
                }
                buttonPlus.id->
                {
                    op="+"
                }
            }
            oldNumber=etShowNumber.text.toString()
            isNewOp=true
            dot=false
        }

        fun buEqualEvent(view: View)
        {
            val newNumber=etShowNumber.text.toString()
            var finalNumber:Double?=null
            when(op)
            {
                "X"->
                {
                    finalNumber=oldNumber.toDouble() * newNumber.toDouble()
                }
                "÷"->
                {
                    finalNumber=oldNumber.toDouble() / newNumber.toDouble()
                }
                "-"->
                {
                    finalNumber=oldNumber.toDouble() - newNumber.toDouble()
                }
                "+"->
                {
                    finalNumber=oldNumber.toDouble() + newNumber.toDouble()
                }
            }
            etShowNumber.setText(finalNumber.toString())
            isNewOp=true
        }

        fun buPercentEvent(view: View)
        {
            val number=(etShowNumber.text.toString().toDouble())/100
            etShowNumber.setText(number.toString())
            isNewOp=true
        }

        fun buCleanEvent(view: View)
        {
            etShowNumber.setText("")
            isNewOp=true
            dot=false
        }

        ////////////////////////////////////////////////////////////////////////////////////
    }
    */

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
        binding.buttonPerc.setOnClickListener(listener)
        binding.buttonDivide.setOnClickListener(listener)
        binding.buttonMultiply.setOnClickListener(listener)
        binding.buttonMinus.setOnClickListener(listener)
        binding.buttonPlus.setOnClickListener(listener)
        binding.buttonDot.setOnClickListener(listener)
        binding.buttonEqual.setOnClickListener(listener)


    }
    val listener = View.OnClickListener { view ->
        when (view.getId()) {
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
            }
            R.id.buttonPlus -> {   //"＋"
                operator = "+"
                screenValue = screenText.toLong()
                //TODO: Add boolean to update number
                screenText = ""
            }
            R.id.buttonMinus -> {
                operator =  "-"
            }
            R.id.buttonMultiply -> {
                operator =  "x"
            }
            R.id.buttonDivide -> {
                operator =  "/"
            }
            R.id.buttonPerc -> {
                // "％"
            }
            R.id.buttonPlusMinus -> {
                // "+/-"
            }
            R.id.buttonEqual -> {
                // "="
                when (operator) {
                    "+" -> {screenText = (screenValue + previousValue).toString()}
                    "-" -> {screenText = (screenValue - previousValue).toString()}
                    "x" -> {screenText = (screenValue * previousValue).toString()}
                    "/" -> {screenText = (screenValue / previousValue).toString()}
                }
            }
            R.id.buttonAC -> {
                screenText = ""
                screenValue = 0
                previousValue = 0
            }
        }
        binding.editTextNumberDecimal.text = screenText
    }




}