package com.eCommerce.shopify.ui.brandproducts.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FavoriteItemLayoutBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.utils.AppConstants.MAX
import com.eCommerce.shopify.utils.AppConstants.MIN

class BrandProductsAdapter:RecyclerView.Adapter<BrandProductsAdapter.BrandProductsViewHolder> {

    private var context: Context
    private var brandProducts:List<Product>
    private var onClickHandler: OnProductClickListener

    constructor(context: Context,brandProducts:List<Product>,clickHandler: OnProductClickListener){
        this.context = context
        this.brandProducts = brandProducts
        this.onClickHandler = clickHandler
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandProductsViewHolder {
        val itemBinding = FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BrandProductsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: BrandProductsViewHolder,
        position: Int
    ) {
        //title
        holder.productTitle.text = brandProducts[position].title

        //image
        Glide.with(context)
            .load(brandProducts[position].image?.src)
            .into(holder.productImg)

        //rate
        //val randomRate: Double = MIN + Math.random() * (MAX - MIN)
        holder.productRate.stepSize = 0.1f
        holder.productRate.rating = brandProducts[position].rate.toFloat()
        Log.i("TAG", "onBindViewHolder: ${brandProducts[position].rate}")

        //price currency
        holder.productCurrency.text = onClickHandler.currencyHandling()

        //price
        if(holder.productCurrency.text == "$") {
            val priceInDollar = brandProducts[position].variants[0].price.toDouble()/18.0
            holder.productPrice.text = String.format("%.2f", priceInDollar)
        }
        else{
            holder.productPrice.text = brandProducts[position].variants[0].price
        }

        //fav icon
        if(brandProducts[position].isFavorite){
            holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_group)
            Log.i("TAG", "onBindViewHolder: adddddddddddddddddddddddddddddddded to favvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv")
        }
        else{
            holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_border_group)
            Log.i("TAG", "onBindViewHolder: remmmmmmmmmmmmmmmmmoooooooooooove from favvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv")
        }

        //linear layout
        holder.linearLayout.setOnClickListener { onClickHandler.onProductItemClick(brandProducts[position].id) }

        //fav btn
        holder.favoriteBtn.setOnClickListener {
            onClickHandler.onFavBtnClick(brandProducts[position])
            if(brandProducts[position].isFavorite){
                holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_group)
                Log.i("TAG", "onBindViewHolder: adddddddddddddddddddddddddddddddded to favvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv")
            }
            else{
                holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_border_group)
                Log.i("TAG", "onBindViewHolder: remmmmmmmmmmmmmmmmmoooooooooooove from favvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv")
            }
        }

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
        val productCurrency = itemBinding.productCurrency
    }

}