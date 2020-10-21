package com.mbds.fragments.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mbds.fragments.R
import com.mbds.fragments.databinding.ComputationFragmentBinding

class ComputationFragment : Fragment(), TextWatcher {

    private lateinit var binding: ComputationFragmentBinding
    private lateinit var operation: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ComputationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.field1.addTextChangedListener(this)
        binding.field2.addTextChangedListener(this)

        selectRadion()

        binding.computeButton.setOnClickListener {
            compute(if (binding.choice.checkedRadioButtonId == -1) binding.choice.checkedRadioButtonId else R.id.add)
        }

        binding.choice.setOnCheckedChangeListener { _, checkedId ->
            compute(checkedId)
        }
    }


    private fun EditText.getNumber() = text.toString().toDouble()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val isEnabled =
            binding.field1.isNotNullOrBlank() && binding.field2.isNotNullOrBlank()

        binding.computeButton.isVisible = isEnabled
        if (isEnabled) {
            compute(if (binding.choice.checkedRadioButtonId == -1) binding.choice.checkedRadioButtonId else R.id.add)
        }
    }

    private fun EditText.isNotNullOrBlank() = text != null && text.isNotBlank()

    private fun compute(chekcedId: Int = R.id.add) {
        val result = when (chekcedId) {
            R.id.add -> "${binding.field1.getNumber().plus(binding.field2.getNumber())}"
            R.id.minus -> "${binding.field1.getNumber().minus(binding.field2.getNumber())}"
            R.id.div -> "${binding.field1.getNumber().div(binding.field2.getNumber())}"
            R.id.mult -> "${binding.field1.getNumber().times(binding.field2.getNumber())}"
            else -> "${binding.field1.getNumber().plus(binding.field2.getNumber())}"
        }
        binding.computeResult.text = result
    }

    private fun selectRadion() = when (operation) {
        "ADD" -> binding.choice.check(R.id.add)
        "MINUS" -> binding.choice.check(R.id.minus)
        "DIV" -> binding.choice.check(R.id.div)
        "TIMES" -> binding.choice.check(R.id.mult)
        else -> binding.choice.check(R.id.add)
    }

    companion object {
        fun newInstance(operation: String): ComputationFragment {
            return ComputationFragment().apply {
                this.operation = operation
            }
        }
    }

}
