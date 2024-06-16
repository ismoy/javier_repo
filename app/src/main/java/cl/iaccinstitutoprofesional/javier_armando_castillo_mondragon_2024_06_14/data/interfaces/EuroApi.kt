package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.interfaces

import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.EuroResponse
import retrofit2.http.GET

interface EuroApi {
    @GET("euro")
    suspend fun getEuroValue():EuroResponse
}