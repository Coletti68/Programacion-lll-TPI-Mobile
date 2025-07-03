

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
import com.example.alquilervehiculos.data.model.AlquilerRequest
import com.example.alquilervehiculos.data.model.Vehiculo
import com.example.alquilervehiculos.ui.auth.ui.login.LoginActivity
import com.example.alquilervehiculos.viewmodel.VehiculoViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: VehiculoViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var vehiculoAdapter: VehiculoAdapter
    private var vehiculoSeleccionado: Vehiculo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<TextView>(R.id.tvWelcome).text = "¡Bienvenido a Alquiler Vehículos!"

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            prefs.edit().remove("token").apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        recyclerView = findViewById(R.id.rvVehiculos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        vehiculoAdapter = VehiculoAdapter(emptyList()) { vehiculo ->
            vehiculoSeleccionado = vehiculo
            Toast.makeText(this, "Seleccionado: ${vehiculo.modelo}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = vehiculoAdapter

        findViewById<Button>(R.id.btnAlquilarAuto).setOnClickListener {
            vehiculoSeleccionado?.let { vehiculo ->
                val usuarioId = prefs.getInt("usuarioId", 0)
                val ahora = LocalDateTime.now()
                val fin = ahora.plusDays(3)
                val formatter = DateTimeFormatter.ISO_DATE_TIME

                val alquiler = AlquilerRequest(
                    usuarioId = usuarioId,
                    vehiculoId = vehiculo.vehiculoId,
                    empleadoId = 1,
                    fechaInicio = formatter.format(ahora),
                    fechaFin = formatter.format(fin),
                    estado = "Pendiente",
                    aceptoTerminos = true
                )

                homeViewModel.crearAlquiler(alquiler) { exito, mensaje ->
                    if (exito) {
                        Toast.makeText(this, "¡Alquiler registrado con éxito!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Error: $mensaje", Toast.LENGTH_LONG).show()
                    }
                }

            } ?: Toast.makeText(this, "Seleccioná un vehículo primero", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnContacto).setOnClickListener {
            Toast.makeText(this, "¡Pantalla de contacto pronto!", Toast.LENGTH_SHORT).show()
        }

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

        homeViewModel.cargarVehiculos()
    }
}
