package com.mbds.fragment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mbds.fragment.R
import com.mbds.fragment.databinding.ComputationFragmentBinding

class ComputationFragment : Fragment() {
    lateinit var binding: ComputationFragmentBinding

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
        binding.calculateButton.setOnClickListener {
            binding.field1.text.toDouble() + binding.field2.text.toDouble()
        }
    }
}