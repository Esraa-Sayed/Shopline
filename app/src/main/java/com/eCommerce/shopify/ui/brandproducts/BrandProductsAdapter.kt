package com.eCommerce.shopify.ui.brandproducts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.databinding.FavoriteItemLayoutBinding
import com.eCommerce.shopify.ui.OnProductClickListener
import com.eCommerce.shopify.ui.favorite.model.Product

class BrandProductsAdapter:RecyclerView.Adapter<BrandProductsAdapter.BrandProductsViewHolder> {

    private var context: Context
    private var brandProducts:List<Product>
    private var onClickHandler:OnProductClickListener

    constructor(context: Context,brandProducts:List<Product>,clickHandler:OnProductClickListener){
        this.context = context
        this.brandProducts = brandProducts
        this.onClickHandler = clickHandler
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

        holder.linearLayout.setOnClickListener { onClickHandler.onProductItemClick() }
        holder.favoriteBtn.setOnClickListener { onClickHandler.onFavBtnClick() }
    }

    override fun getItemCount(): Int {
        return brandProducts.size
    }

    fun setBrandProductsList(brandProducts:List<Product>){
        this.brandProducts = brandProducts
    }

    inner class BrandProductsViewHolder(private val itemBinding: FavoriteItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val linearLayout = itemBinding.favoriteItemLinear
        val productTitle = itemBinding.productTitle
        val productPrice = itemBinding.productPrice
        val productImg = itemBinding.productImg
        val productRate = itemBinding.productRate
        val favoriteBtn = itemBinding.favoriteBtn
    }

}