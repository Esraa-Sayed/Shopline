package com.eCommerce.shopify.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FavoriteItemLayoutBinding
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

        val itemBinding = FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.productTitle.text = favProducts[position].name
        holder.productPrice.text = favProducts[position].price.toString()
        //holder.productImg.setImageResource(R.drawable.t_shirt_pink)
        Glide.with(context)
            .load(favProducts[position].img)
            .into(holder.productImg)
        holder.productRate.rating = favProducts[position].rate.toFloat()
    }

    override fun getItemCount(): Int {
        return favProducts.size
    }

    fun setFavProductList(favProducts:List<Product>){
        this.favProducts = favProducts
    }

    inner class FavoriteViewHolder(private val itemBinding: FavoriteItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val productTitle: TextView
            get() = itemBinding.productTitle
        val productPrice: TextView
            get() = itemBinding.productPrice
        val productImg: ImageView
            get() = itemBinding.productImg
        val productRate:RatingBar
            get() = itemBinding.productRate
    }
}