package com.eCommerce.shopify.ui.product.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.SmartCollection
import com.eCommerce.shopify.utils.AppConstants.MAX
import com.eCommerce.shopify.utils.AppConstants.MIN
import com.eCommerce.shopify.utils.AppConstants.playAnimation
import java.lang.String
import java.util.*
import kotlin.math.roundToInt

class ProductAdapter(
    var context: Context,
    private var productList: List<Product>,
    private var onCategoryProductClickListener: OnCategoryProductClickListener
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        playAnimation(holder.itemView, context, R.anim.open_view_zoom_in_fade_in)

        holder.imgViewProduct?.let {
            Glide
                .with(context)
                .load(productList[position].image?.src)
                .error(R.drawable.no_category)
                .placeholder(R.drawable.no_category)
                .into(it)
        }

        val words = productList[position].title.lowercase().split(" ")
        var companyName = ""
        words.forEach { word ->
            companyName += word.replaceFirstChar { it.uppercase() } + " "
        }

        holder.txtViewProduct?.text = companyName.trim()

        val randomRate: Double = MIN + Math.random() * (MAX - MIN)

        holder.ratingBarProduct?.stepSize = 0.1f
        holder.ratingBarProduct?.rating = randomRate.toFloat()
        holder.txtViewRating?.text = "(".plus((randomRate * 100.0).roundToInt() / 100.0).plus(")")

        holder.itemView.setOnClickListener {
            onCategoryProductClickListener.onCategoryProductClick(productList[position])
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewProduct: ImageView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.imgViewProduct)
                }
                return field
            }
            private set
        var txtViewProduct: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.txtViewProduct)
                }
                return field
            }
            private set
        var ratingBarProduct: RatingBar? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.ratingBarProduct)
                }
                return field
            }
            private set
        var txtViewRating: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.txtViewRating)
                }
                return field
            }
            private set
    }
}