package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.R
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Product
class ProductAdapter(private val onDeleteClick: (Product) -> Unit) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_product_item, parent, false)
        return ProductViewHolder(view, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductViewHolder(itemView: View, private val onDeleteClick: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nameProductItem)
        private val cantidadTextView: TextView = itemView.findViewById(R.id.quantityProductItem)
        private val precioEurosTextView: TextView = itemView.findViewById(R.id.priceEuroItem)
        private val precioPesosTextView: TextView = itemView.findViewById(R.id.pricePesosItem)
        private val lugarExportacionTextView: TextView = itemView.findViewById(R.id.stateExportationItem)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.deleteItem)

        fun bind(producto: Product) {
            nombreTextView.text = producto.name
            cantidadTextView.text = producto.quantity.toString()
            precioEurosTextView.text = producto.priceEuros.toString()
            precioPesosTextView.text = producto.pricePesos.toString()
            lugarExportacionTextView.text = producto.lugarExportation

            deleteIcon.setOnClickListener {
                onDeleteClick(producto)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
