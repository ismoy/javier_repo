package cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import cl.iaccinstitutoprofesional.javier_armando_castillo_mondragon_2024_06_14.domain.entities.Product

class BarChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var products: List<Product> = emptyList()
    private val paint = Paint()

    fun setProducts(products: List<Product>) {
        this.products = products.sortedByDescending { it.pricePesos }.take(4)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (products.isEmpty()) return

        val barWidth = (width / (products.size * 2)).toFloat()
        val maxPrice = products.maxOfOrNull { it.pricePesos!!.toDouble() } ?: 0.0
        val scale = if (maxPrice > 0) height / maxPrice.toFloat() else 1f

        val colors = listOf(Color.GREEN, Color.YELLOW, Color.CYAN, Color.RED)

        for ((index, product) in products.withIndex()) {
            paint.color = colors[index % colors.size]
            val left = index * 2 * barWidth
            val top = height - (product.pricePesos!! * scale).toFloat()
            val right = left + barWidth
            val bottom = height.toFloat()

            canvas.drawRect(left, top, right, bottom, paint)

            paint.color = Color.BLACK
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = 30f
            product.name?.let { canvas.drawText(it, left + barWidth / 2, height - 10f, paint) }
        }
    }
}
