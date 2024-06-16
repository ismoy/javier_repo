package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name:String?=null,
    val quantity:Int?=null,
    val priceEuros:Double?=null,
    val pricePesos:Double?=null,
    val lugarExportation:String?=null
)
