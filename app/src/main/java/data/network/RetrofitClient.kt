package data.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import data.network.AuthService

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000/api/" // si estás en emulador

    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}