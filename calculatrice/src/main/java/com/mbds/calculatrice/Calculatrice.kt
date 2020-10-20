package com.mbds.calculatrice

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.mbds.calculatrice.databinding.ActivityCalculatriceBinding


class Calculatrice : AppCompatActivity() {
    lateinit var binding: ActivityCalculatriceBinding
    lateinit var root: ConstraintLayout
    var resetAfterResult: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatriceBinding.inflate(LayoutInflater.from(baseContext))
        root = binding.root
        setContentView(root)
        initView()
    }

    private fun setListenerOnClick(button: Button) {
        when (button.id) {
            R.id.buttonDelete -> button.setOnClickListener { binding.display.text = "" }
            R.id.buttonEquals -> button.setOnClickListener { displayResult() }
            else -> button.setOnClickListener {
                if(resetAfterResult) {
                    binding.display.text = button.text.toString()
                    resetAfterResult = false
                } else {
                    binding.display.text = binding.display.text.toString() + button.text.toString()
                }
            }
        }
    }

    private fun initView() {
        for (i in 0 until root.childCount) {
            if (root.get(i) is Button) setListenerOnClick(root[i] as Button)
        }
    }

    private fun displayResult() {
        var expression = binding.display.text
        val splitList = splitExpression(expression.toString())
        calculateResult(splitList)
    }

    private fun splitExpression(expr: String): MutableList<String> {
        val operators = listOf('+', 'X', '-', '%')
        var lastOperatorPos = -1
        val result = mutableListOf<String>()
        for (i in expr.indices) {
            if (i == expr.length - 1) {
                if (operators.contains(expr[i])) {
                    result.add(expr.substring(lastOperatorPos + 1, i))
                    result.add(expr[i].toString())
                } else {
                    result.add(expr.substring(lastOperatorPos + 1))
                }
            } else if (operators.contains(expr[i])) {
                if(i != 0) {
                    result.add(expr.substring(lastOperatorPos + 1, i))
                }
                result.add(expr[i].toString())
                lastOperatorPos = i
            }
        }
        return result
    }

    private fun calculateResult(splitList: MutableList<String>) {
        when {
            splitList.contains("X") -> {
                val index = splitList.indexOf("X")
                if(isOperatorInvalid(index, splitList)) { triggerErrorMessage(); return }
                val result = splitList[index - 1].toDouble() * splitList[index + 1].toDouble()
                calculateResult(replaceAndRemoveElement(splitList, result.toString(), index))
            }
            splitList.contains("%") -> {
                val index = splitList.indexOf("%")
                if(isOperatorInvalid(index, splitList)) { triggerErrorMessage(); return }
                val result = splitList[index - 1].toDouble() / splitList[index + 1].toDouble()
                calculateResult(replaceAndRemoveElement(splitList, result.toString(), index))
            }
            splitList.contains("+") -> {
                val index = splitList.indexOf("+")
                if(isOperatorInvalid(index, splitList)) { triggerErrorMessage(); return }
                val result = splitList[index - 1].toDouble() + splitList[index + 1].toDouble()
                calculateResult(replaceAndRemoveElement(splitList, result.toString(), index))
            }
            splitList.contains("-") -> {
                when(val index = splitList.indexOf("-")) {
                    // When the '-' operator means a negative number
                    0 -> {
                        splitList[0] = "-" + splitList[index + 1]
                        splitList.removeAt(index + 1)
                        calculateResult(splitList)
                    }
                    (splitList.size - 1) -> {
                        triggerErrorMessage();
                        return
                    }
                    else -> {
                        val result = splitList[index - 1].toDouble() - splitList[index + 1].toDouble()
                        calculateResult(
                            replaceAndRemoveElement(
                                splitList,
                                result.toString(),
                                index
                            )
                        )
                    }
                }
            }
            else -> {
                binding.display.text = splitList[0]
                resetAfterResult = true
            }
        }
    }

    private fun replaceAndRemoveElement(splitList: MutableList<String>, result: String, index: Int): MutableList<String> {
        splitList[index - 1] = result

        // Remove the operator and the second part of the expression, one after another
        splitList.removeAt(index)
        splitList.removeAt(index)

        return splitList
    }

    private fun triggerErrorMessage() {
        binding.display.text = getString(R.string.error_message)
        resetAfterResult = true
    }

    private fun isOperatorInvalid(index: Int, splitList: MutableList<String>): Boolean {
        return index == 0 || (index == splitList.size - 1)
    }
}
