package com.eCommerce.shopify.ui.brandproducts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FavoriteItemLayoutBinding
import com.eCommerce.shopify.ui.favorite.model.Product

class BrandProductsAdapter:RecyclerView.Adapter<BrandProductsAdapter.BrandProductsViewHolder> {

    private var context: Context
    private var brandProducts:List<Product>

    constructor(context: Context,brandProducts:List<Product>){
        this.context = context
        this.brandProducts = brandProducts
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandProductsAdapter.BrandProductsViewHolder {
        val itemBinding = FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BrandProductsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: BrandProductsAdapter.BrandProductsViewHolder,
        position: Int
    ) {
        holder.productTitle.text = brandProducts[position].name
        holder.productPrice.text = brandProducts[position].price.toString()
        //holder.productImg.setImageResource(R.drawable.t_shirt_pink)
        Glide.with(context)
            .load(brandProducts[position].img)
            .into(holder.productImg)
        holder.productRate.rating = brandProducts[position].rate.toFloat()
    }

    override fun getItemCount(): Int {
        return brandProducts.size
    }

    fun setBrandProductsList(brandProducts:List<Product>){
        this.brandProducts = brandProducts
    }

    inner class BrandProductsViewHolder(private val itemBinding: FavoriteItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val productTitle: TextView
            get() = itemBinding.productTitle
        val productPrice: TextView
            get() = itemBinding.productPrice
        val productImg: ImageView
            get() = itemBinding.productImg
        val productRate: RatingBar
            get() = itemBinding.productRate
    }

}