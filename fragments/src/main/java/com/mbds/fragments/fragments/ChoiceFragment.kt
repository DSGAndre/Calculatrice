package com.mbds.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.mbds.fragments.MainActivity
import com.mbds.fragments.changeFragment
import com.mbds.fragments.databinding.FragmentChoiceBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ChoiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChoiceFragment : Fragment() {

    lateinit var binding: FragmentChoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.children.filter {
            it is Button
        }.forEach {
            it.setOnClickListener { view ->
                (activity as? MainActivity)?.changeFragment(
                    ComputationFragment.newInstance(
                        view.tag?.toString() ?: "ADD"
                    )
                )
            }
        }
    }
}