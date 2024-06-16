package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.R.id
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.R.layout
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.local.AppDatabase
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.data.network.RetrofitInstance
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Product
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.ui.adapter.ProductAdapter
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.ui.presentation.ProductViewModel
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.ui.presentation.ProductViewModelFactory
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.utils.BarChartView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var btnAdd:MaterialButton
    private var valorToChange:Double?=null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:ProductAdapter
    private lateinit var noData:TextView
    private lateinit var barChartView: BarChartView
    private lateinit var showGraphic:MaterialButton
    private var product = mutableListOf<Product>()
    private lateinit var containerPrincipal:ConstraintLayout
    private lateinit var containerSecondary:ConstraintLayout
    private lateinit var cancelChar:MaterialButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_main)
        val database = AppDatabase.getDatabase(applicationContext)
        val factory = ProductViewModelFactory(RetrofitInstance.api, database.productDao())
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        recyclerView = findViewById(id.recycleView)
        showGraphic = findViewById(id.showGraphic)
        containerPrincipal = findViewById(id.containerPrincipal)
        containerSecondary = findViewById(id.containerChar)
        cancelChar = findViewById(id.cancelChar)
        noData = findViewById(id.noData)
        adapter = ProductAdapter { product ->
            showDeleteConfirmationDialog(product)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        btnAdd = findViewById(R.id.addProduct)
        btnAdd.setOnClickListener {
           showAddProductDialog()
        }
        showGraphic.setOnClickListener {
            containerPrincipal.isGone = true
            containerSecondary.isGone = false
        }
        cancelChar.setOnClickListener {
            containerPrincipal.isGone = false
            containerSecondary.isGone = true
        }
        productViewModel.fetchProducts()
        barChartView = findViewById(id.barChartView)

        lifecycleScope.launch {
            productViewModel.products.observe(this@MainActivity) { result ->
                val today = LocalDate.now()
                val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

                val closestSerie = result.minByOrNull {
                    val serieDate = LocalDate.parse(it.fecha, formatter)
                    val daysBetween = java.time.Duration.between(today.atStartOfDay(), serieDate.atStartOfDay()).toDays()
                    abs(daysBetween)
                }

                closestSerie?.let {
                    valorToChange = it.valor
                    Log.e("ValorResponse:", valorToChange.toString())
                }
            }
        }
        productViewModel.fetchAllProduct()
       lifecycleScope.launch {
           productViewModel.allProduct.observe(this@MainActivity){result->
               adapter.submitList(result){
                   if (result.isEmpty()) {
                       noData.isGone =false
                       barChartView.setProducts(result)
                       showGraphic.isGone = true
                       adapter.notifyDataSetChanged()

                   }else{
                       noData.isGone = true
                       product = result.toMutableList()
                       showGraphic.isGone = false
                       barChartView.setProducts(product)
                   }
               }

           }
       }


    }

    @SuppressLint("MissingInflatedId")
    private fun showAddProductDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_productt, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Agregar Producto")
            .setPositiveButton("Agregar", null)
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .create()

        dialogBuilder.setOnShowListener { dialogInterface ->
            val button = (dialogInterface as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                val nameProduct = dialogView.findViewById<TextInputEditText>(R.id.nameEdt).text.toString()
                val quantity = dialogView.findViewById<TextInputEditText>(R.id.quantityEdt).text.toString()
                val priceEuros = dialogView.findViewById<TextInputEditText>(R.id.priceEurosEdt).text.toString()
                val lugarExportation = dialogView.findViewById<TextInputEditText>(R.id.stateExportationEdt).text.toString()

                if (nameProduct.isBlank() || quantity.isBlank() || priceEuros.isBlank() || lugarExportation.isBlank()) {
                    Toast.makeText(this, "Los campos son requeridos", Toast.LENGTH_SHORT).show()
                } else {
                    val producto = Product(
                        name = nameProduct,
                        quantity = quantity.toInt(),
                        priceEuros = priceEuros.toDouble(),
                        pricePesos = priceEuros.toDouble() * (valorToChange ?: 1.0),
                        lugarExportation = lugarExportation
                    )
                    productViewModel.addProduct(producto)
                    Toast.makeText(this, "Producto cargado correctamente", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
            }
        }

        dialogBuilder.show()
    }
    private fun showDeleteConfirmationDialog(product: Product) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Producto")
            .setMessage("¿Estás seguro de que quieres eliminar este producto?")
            .setPositiveButton("Sí") { dialog, _ ->
                productViewModel.deleteProduct(product)
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}