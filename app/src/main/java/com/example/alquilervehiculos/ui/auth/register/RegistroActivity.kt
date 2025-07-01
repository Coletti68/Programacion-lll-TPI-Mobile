package com.example.alquilervehiculos.ui.auth.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alquilervehiculos.R
import com.example.alquilervehiculos.databinding.ActivityRegistroBinding
import androidx.activity.viewModels
import com.example.alquilervehiculos.viewmodel.AuthViewModel



class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.registroSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                finish() // Vuelve a login
            } else {
                Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val nombre = binding.etNombreCompleto.text.toString()
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            val dni = binding.etDNI.text.toString()
            viewModel.register(nombre, email, pass, dni)
        }

    }
}
