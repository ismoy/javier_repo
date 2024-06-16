package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase

import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.repository.ProductRepository
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Product

class GetAllProductUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke():List<Product> = repository.getAllProduct()
}