package ui.auth.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.LoginRequest
import data.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val loginExitoso = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(context: Context, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.authService.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    guardarToken(context, token)
                    loginExitoso.value = true
                } else {
                    error.value = "Email o contraseña inválidos"
                }
            } catch (e: Exception) {
                error.value = "Error de red: ${e.localizedMessage}"
            }
        }
    }

    private fun guardarToken(context: Context, token: String) {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("jwt", token).apply()
    }
}