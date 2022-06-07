package com.eCommerce.shopify.ui.favorite.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FavoriteItemLayoutBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.brandproducts.view.OnProductClickListener

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private var context: Context
    private var favProducts:List<Product>
    private var onClickHandler: OnProductClickListener

    constructor(context: Context, favProducts:List<Product>,onClickHandler: OnProductClickListener){
        this.context = context
        this.favProducts = favProducts
        this.onClickHandler = onClickHandler
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {

        val itemBinding = FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.productTitle.text = favProducts[position].title
        holder.productPrice.text = favProducts[position].variants[0].price
        //holder.productImg.setImageResource(R.drawable.t_shirt_pink)
        Glide.with(context)
            .load(favProducts[position].image?.src)
            .into(holder.productImg)
        holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_group)
        //holder.productRate.rating = favProducts[position].rate.toFloat()

        holder.linearLayout.setOnClickListener { onClickHandler.onProductItemClick(favProducts[position].id) }
        holder.favoriteBtn.setOnClickListener {
            onClickHandler.onFavBtnClick(favProducts[position])
        }
    }

    override fun getItemCount(): Int {
        return favProducts.size
    }

    fun setFavProductList(favProducts:List<Product>){
        this.favProducts = favProducts
    }

    inner class FavoriteViewHolder(private val itemBinding: FavoriteItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val linearLayout = itemBinding.favoriteItemLinear
        val productTitle = itemBinding.productTitle
        val productPrice = itemBinding.productPrice
        val productImg = itemBinding.productImg
        val productRate = itemBinding.productRate
        val favoriteBtn = itemBinding.favoriteBtn
    }
}