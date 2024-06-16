package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.network

import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.interfaces.EuroApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val BASE_URL = "https://mindicador.cl/api/"

    val api:EuroApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EuroApi::class.java)
    }
}