package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.interfaces.EuroApi
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.local.ProductDao
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.repository.ProductRepository
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase.AddProductUseCase
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase.DeleteProductUseCase
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase.GetAllProductUseCase
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase.GetProductUseCase

class ProductViewModelFactory(private val euroApi: EuroApi,private val productDao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(GetProductUseCase(ProductRepository(euroApi,productDao)),
                getAllProductUseCase = GetAllProductUseCase(ProductRepository(euroApi, productDao)),
                addProductUseCase = AddProductUseCase(ProductRepository(euroApi, productDao)),
                deleteProductUseCase = DeleteProductUseCase(ProductRepository(euroApi, productDao))
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}