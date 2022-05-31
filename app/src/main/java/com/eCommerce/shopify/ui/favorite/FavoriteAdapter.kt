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
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.OnProductClickListener

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
        //holder.productPrice.text = favProducts[position].price.toString()
        //holder.productImg.setImageResource(R.drawable.t_shirt_pink)
        Glide.with(context)
            .load(favProducts[position].productImage.src)
            .into(holder.productImg)
        //holder.productRate.rating = favProducts[position].rate.toFloat()

        holder.linearLayout.setOnClickListener { onClickHandler.onProductItemClick() }
        holder.favoriteBtn.setOnClickListener { onClickHandler.onFavBtnClick() }
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