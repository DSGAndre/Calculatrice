package com.mbds.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mbds.fragment.fragments.ComputationFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeFragment()
    }

    private fun changeFragment(){
        // create an instance of ComputationFragment
        val fragment = ComputationFragment()
        // create a transaction through the fragment manager
        supportFragmentManager.beginTransaction().apply {
            // replace former fragment, if existing
            replace(R.id.fragment_container, fragment)
            // Add the transaction in the stack
            addToBackStack(null)
        }.commit()
    }
}
