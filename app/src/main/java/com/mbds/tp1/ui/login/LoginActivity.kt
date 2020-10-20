package com.mbds.tp1.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mbds.tp1.MainActivity
import com.mbds.tp1.R
import com.mbds.tp1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(baseContext))
        setContentView(binding.root)
        initView()
    }

    fun initView() {

        binding.login.setOnClickListener {
            handleClick()
        }
    }

    fun handleClick() {
        if (!(binding.email.text.isNullOrBlank() || binding.password.text.isNullOrBlank())) {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(
                baseContext,
                " Veuillez renseigné un email et un mot de passe !",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.login.isEnabled = false
    }
}