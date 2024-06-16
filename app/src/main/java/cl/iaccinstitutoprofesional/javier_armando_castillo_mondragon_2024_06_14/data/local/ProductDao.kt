package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getAllProducts():List<Product>

    @Insert
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product): Int

}