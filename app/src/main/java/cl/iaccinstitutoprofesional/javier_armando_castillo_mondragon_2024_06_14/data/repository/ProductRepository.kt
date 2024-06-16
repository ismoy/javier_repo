package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.repository

import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.interfaces.EuroApi
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.local.ProductDao
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.EuroResponse
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Product
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.interfaces.IProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val euroApi:EuroApi,private val productDao: ProductDao):IProductRepository {
    override suspend fun getProducts(): EuroResponse {
        return withContext(Dispatchers.IO){
            euroApi.getEuroValue()
        }

    }

    override suspend fun getAllProduct(): List<Product> {
        return withContext(Dispatchers.IO){
            productDao.getAllProducts()
        }
    }

    override suspend fun addProduct(product: Product) {
        return withContext(Dispatchers.IO){
            productDao.insertProduct(product)
        }
    }

    override suspend fun deleteProduct(product: Product) {
        withContext(Dispatchers.IO) {
            productDao.deleteProduct(product)
        }
    }

}