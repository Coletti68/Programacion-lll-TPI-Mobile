package com.example.alquilervehiculos.ui.auth.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alquilervehiculos.databinding.ActivityHomeBinding
import com.example.alquilervehiculos.ui.auth.ui.login.LoginActivity


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVehicles.setOnClickListener {
            startActivity(Intent(this, VehicleListActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            // Limpia token y vuelve al login
            getSharedPreferences("prefs", MODE_PRIVATE).edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
