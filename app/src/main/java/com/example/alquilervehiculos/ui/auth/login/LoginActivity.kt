package com.example.alquilervehiculos.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.alquilervehiculos.databinding.ActivityLogin2Binding
import com.example.alquilervehiculos.viewmodel.AuthViewModel




class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin2Binding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }

        viewModel.loginResult.observe(this) { result ->
            if (result != null) {
                saveToken(result.token)
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                // ir a home
            } else {
                Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToken(token: String) {
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        prefs.edit().putString("token", token).apply()
    }
}