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
    private lateinit var recyclerView: RecyclerView
    private lateinit var vehiculoAdapter: VehiculoAdapter
    private var vehiculoSeleccionado: Vehiculo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 馃帀 Mensaje de bienvenida
        findViewById<TextView>(R.id.tvWelcome).text = "隆Bienvenido a Alquiler Veh铆culos!"

        // 馃敁 Cerrar sesi贸n
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            prefs.edit().remove("token").apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // 馃摝 Configuraci贸n del RecyclerView
        recyclerView = findViewById(R.id.rvVehiculos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        vehiculoAdapter = VehiculoAdapter(emptyList()) { vehiculo ->
            vehiculoSeleccionado = vehiculo
            Toast.makeText(this, "Seleccionado: ${vehiculo.modelo}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = vehiculoAdapter

        // 馃殫 Bot贸n "Alquilar"
        findViewById<Button>(R.id.btnAlquilarAuto).setOnClickListener {
            vehiculoSeleccionado?.let {
                Toast.makeText(this, "隆Alquilaste el ${it.modelo} (${it.placa})!", Toast.LENGTH_LONG).show()
            } ?: Toast.makeText(this, "Seleccion谩 un veh铆culo primero", Toast.LENGTH_SHORT).show()
        }

        // 馃摓 Bot贸n "Contacto"
        findViewById<Button>(R.id.btnContacto).setOnClickListener {
            Toast.makeText(this, "隆Pantalla de contacto pronto!", Toast.LENGTH_SHORT).show()
        }

        // 馃憖 Observadores
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvError = findViewById<TextView>(R.id.tvError)

        homeViewModel.vehiculos.observe(this) { lista ->
            vehiculoAdapter = VehiculoAdapter(lista) { vehiculo ->
                vehiculoSeleccionado = vehiculo
                Toast.makeText(this, "Seleccionado: ${vehiculo.modelo}", Toast.LENGTH_SHORT).show()
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

        // 馃殌 Cargar datos
        homeViewModel.cargarVehiculos()
    }
}