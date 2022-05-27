package com.eCommerce.shopify.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.ui.favorite.model.Product

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private var context: Context
    private var favProducts:List<Product>

    constructor(context: Context, favProducts:List<Product>){
        this.context = context
        this.favProducts = favProducts
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item_layout,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.productTitle.text = favProducts[position].name
        holder.productPrice.text = favProducts[position].price.toString()
        holder.productImg.setImageResource(R.drawable.t_shirt_pink)
        holder.productRate.rating = favProducts[position].rate.toFloat()
    }

    override fun getItemCount(): Int {
        return favProducts.size
    }

    fun setFavProductList(favProducts:List<Product>){
        this.favProducts = favProducts
    }

    inner class FavoriteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val productTitle: TextView
            get() = view.findViewById(R.id.productTitle)
        val productPrice: TextView
            get() = view.findViewById(R.id.productPrice)
        val productImg: ImageView
            get() = view.findViewById(R.id.productImg)
        val productRate:RatingBar
            get() = view.findViewById(R.id.productRate)
    }
}