package com.example.alquilervehiculos.ui.auth.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alquilervehiculos.R
import com.example.alquilervehiculos.adapter.VehiculoAdapter
import com.example.alquilervehiculos.data.model.Vehiculo
import com.example.alquilervehiculos.ui.auth.ui.login.LoginActivity
import com.example.alquilervehiculos.viewmodel.VehiculoViewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: VehiculoViewModel by viewModels()
    private lateinit var vehiculoAdapter: VehiculoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 🪪 Mostrar token
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "No disponible")
        findViewById<TextView>(R.id.tvWelcome).text = "Token actual:\n$token"

        // 🔓 Botón cerrar sesión
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            prefs.edit().remove("token").apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Navegación
        findViewById<Button>(R.id.btnAlquilarAuto).setOnClickListener {
            // Podés dejarlo vacío por ahora o comentar si no tenés la actividad
        }

        findViewById<Button>(R.id.btnContacto).setOnClickListener {
            // Podés dejarlo vacío por ahora o comentar si no tenés la actividad
        }

        // 🔄 RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rvVehiculos)
        vehiculoAdapter = VehiculoAdapter(emptyList()) { vehiculo ->
            Toast.makeText(this, "Alquilar: ${vehiculo.modelo}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.apply {
            adapter = vehiculoAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }

        // 👀 Observadores
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvError = findViewById<TextView>(R.id.tvError)

        homeViewModel.vehiculos.observe(this) { lista ->
            vehiculoAdapter = VehiculoAdapter(lista) { vehiculo ->
                Toast.makeText(this, "Alquilar: ${vehiculo.modelo}", Toast.LENGTH_SHORT).show()
            }
            recyclerView.adapter = vehiculoAdapter
        }

        homeViewModel.isLoading.observe(this) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        homeViewModel.error.observe(this) { mensaje ->
            tvError.visibility = if (mensaje != null) View.VISIBLE else View.GONE
            tvError.text = mensaje ?: ""
        }

        // 🚀 Cargar vehículos al iniciar
        homeViewModel.cargarVehiculos()
    }
}