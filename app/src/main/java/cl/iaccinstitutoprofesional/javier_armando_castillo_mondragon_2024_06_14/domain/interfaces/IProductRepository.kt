package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.interfaces

import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.EuroResponse
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Product

interface IProductRepository {
    //Get data value to api
    suspend fun getProducts():EuroResponse
    // get Product added to room persistent
    suspend fun getAllProduct():List<Product>
    // add product to room persistent
    suspend fun addProduct(product: Product)
    //delete product
    suspend fun deleteProduct(product: Product)
}