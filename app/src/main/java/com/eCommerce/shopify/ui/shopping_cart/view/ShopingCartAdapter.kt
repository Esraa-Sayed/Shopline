package com.eCommerce.shopify.ui.shopping_cart.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.databinding.ShoppingCartRowBinding
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.utils.AppConstants

class ShopingCartAdapter(var listner: Listner, var productDetail: List<ProductDetail>?, val currency: String) : RecyclerView.Adapter<ShopingCartAdapter.ShoppingCartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val itemBinding = ShoppingCartRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {

        var priceMultiplier = 1.0
        if(currency != AppConstants.EGP){
            priceMultiplier /= 10
        }
        val priceDouble = ((productDetail?.get(position)?.variants?.get(0)?.price)?.toDouble() ?: 00.00) * priceMultiplier
        val price = String.format("%.2f", priceDouble) + " " + currency

        productDetail?.get(position)?.let {
            productDetail?.get(position)?.let { it1 ->
                    holder.bind(productDetail?.get(position)!!.image.src, it.title, it1.amount,
                        price)
            }
        }
        holder._bindView.scRowDeleteBtn.setOnClickListener{
            productDetail?.get(position)?.let { it1 -> listner.checkToDelete(it1) }
        }
        holder._bindView.minusBtn.setOnClickListener {
            if(productDetail?.get(position)?.amount!! > 1){
                productDetail?.get(position)?.let { it.amount-- }
                holder._bindView.amount.text = productDetail?.get(position)?.amount.toString()
                productDetail?.get(position)?.let { it1 -> listner.update(it1) }
            }
        }
        holder._bindView.plusBtn.setOnClickListener {
            productDetail?.get(position)?.let { it.amount++ }
            holder._bindView.amount.text = productDetail?.get(position)?.amount.toString()
            productDetail?.get(position)?.let { it1 -> listner.update(it1) }
        }
    }

    override fun getItemCount(): Int {
        //return shoppingCarts.size
        return productDetail!!.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ProductDetail>) {
        productDetail = data
        notifyDataSetChanged()
    }

    class ShoppingCartViewHolder(val _bindView: ShoppingCartRowBinding)
        : RecyclerView.ViewHolder(_bindView.root) {

        fun bind(imageUrl: String, name: String, amount: Int, price: String){
            Glide.with(_bindView.root.context)
                .load(imageUrl)
                .into(_bindView.scRowItemImage)
            _bindView.scRowItemName.text = name
            _bindView.scRowItemPrice.text = price
            _bindView.amount.text = amount.toString()
        }
        fun bindAmount(amount: Int){
            _bindView.amount.text = amount.toString()
        }
    }
}