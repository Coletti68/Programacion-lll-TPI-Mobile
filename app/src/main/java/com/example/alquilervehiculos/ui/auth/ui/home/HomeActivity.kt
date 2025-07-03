package com.example.alquilervehiculos.ui.auth.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.alquilervehiculos.R
import com.example.alquilervehiculos.ui.auth.ui.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 🪪 Obtener el token guardado
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "No disponible")

        // 💬 Mostrar el token o un mensaje de bienvenida
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        tvWelcome.text = "Token actual:\n$token"

        // 🔓 Botón para cerrar sesión
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            prefs.edit().remove("token").apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}