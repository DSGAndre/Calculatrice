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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatriceBinding.inflate(LayoutInflater.from(baseContext))
        root = binding.root
        setContentView(root)
        initView()

    }

    fun setListenerOnClick(button: Button) {

        print(button.id.toString())
        when (button.id) {
            R.id.buttonDelete -> button.setOnClickListener { binding.display.text = "" }
            R.id.buttonEquals -> button.setOnClickListener { displayResult() }
            else -> button.setOnClickListener {
                binding.display.text = binding.display.text.toString() + button.text.toString()
            }
        }
    }

    fun initView() {
        for (i in 0 until root.childCount) {
            if (root.get(i) is Button) setListenerOnClick(root[i] as Button)
        }
    }

    fun displayResult() {
        var expression = binding.display.text
        val splitedList = splitExpression(expression.toString())
        calculateResult(splitedList)

    }

    fun splitExpression(expr: String): MutableList<String> {
        val operators = listOf('+', '*', '-', '%')
        var lastOperatorPos = -1
        val result = mutableListOf<String>()
        for (i in expr.indices) {
            if (i == expr.length - 1) {
                result.add(expr.substring(lastOperatorPos + 1))
            } else if (operators.contains(expr[i])) {
                result.add(expr.substring(lastOperatorPos + 1, i))
                result.add(expr[i].toString())
                lastOperatorPos = i
            }
        }
        return result
    }

    fun calculateResult(splitedList: MutableList<String>) {

        when {
            splitedList.contains("X") -> {
                val index = splitedList.indexOf("X")
                val result = splitedList[index - 1].toInt() * splitedList[index + 1].toInt()
                calculateResult(replaceAndRemoveElement(splitedList, result.toString(), index))
            }
            splitedList.contains("%") -> {
                val index = splitedList.indexOf("%")
                val result = splitedList[index - 1].toInt() / splitedList[index + 1].toInt()
                calculateResult(replaceAndRemoveElement(splitedList, result.toString(), index))
            }
            splitedList.contains("+") -> {
                val index = splitedList.indexOf("+")
                val result = splitedList[index - 1].toInt() + splitedList[index + 1].toInt()
                calculateResult(replaceAndRemoveElement(splitedList, result.toString(), index))
            }
            splitedList.contains("-") -> {
                val index = splitedList.indexOf("X")
                val result = splitedList[index - 1].toInt() - splitedList[index + 1].toInt()
                calculateResult(replaceAndRemoveElement(splitedList, result.toString(), index))
            }
            else -> {
                print(splitedList[0])
                binding.display.text = splitedList[0]
            }
        }
    }

    private fun replaceAndRemoveElement(splitedList: MutableList<String>, result: String, index: Int): MutableList<String> {
        var newList = splitedList
        newList[index-1] = result
        newList.removeAt(index)
        newList.removeAt(index)
        print(newList[0])
        return newList
    }


}
