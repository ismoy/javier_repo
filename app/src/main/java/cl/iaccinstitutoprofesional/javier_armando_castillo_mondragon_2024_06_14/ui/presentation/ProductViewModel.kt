package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.ui.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Product
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Serie
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase.AddProductUseCase
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase.DeleteProductUseCase
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase.GetAllProductUseCase
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.useCase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val getProductUseCase: GetProductUseCase,
    private val getAllProductUseCase: GetAllProductUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase):ViewModel() {

    private var _products = MutableLiveData<List<Serie>>(emptyList())
    val products: LiveData<List<Serie>> = _products
    private var _errorResponse = MutableLiveData("")
    val errorResponse = _errorResponse

    private var _allProduct = MutableLiveData<List<Product>>(emptyList())
    val allProduct:LiveData<List<Product>> = _allProduct


    fun fetchProducts(){
        viewModelScope.launch {
            try {
             val response = getProductUseCase.invoke()
                _products.value =response.serie
                Log.i("ResponseApiData","${response.serie}")
            }catch (e:Exception){
                _errorResponse.value =e.message.toString()
                Log.e("ErrorResponseApiData:", "${e.message}")
            }
        }
    }

    fun fetchAllProduct(){
        viewModelScope.launch {
           try {
               val result = getAllProductUseCase.invoke()
               _allProduct.value =result
               Log.i("ResultPersistentData:","$result")
           }catch (e:Exception){
               Log.e("ErrorReadDataEnRoom:" ,e.message.toString())
           }
        }
    }

    fun addProduct(product: Product){
        viewModelScope.launch {
            addProductUseCase(product)
            _allProduct.value =getAllProductUseCase.invoke()
            Log.i("ADDDataEnRoom","Product Added in Room DataBase")
        }
    }
    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase.invoke(product)
            fetchAllProduct()
        }
    }
}