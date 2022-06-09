package com.eCommerce.shopify.ui.shopping_cart.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.databinding.ShoppingCartRowBinding
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.utils.AppConstants

class ShoppingCartAdapter(private var listener: Listener, private var productDetailList: List<ProductDetail>?, val currency: String) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val itemBinding: ShoppingCartRowBinding = ShoppingCartRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {

        var priceMultiplier = 1.0
        if(currency != AppConstants.EGP){
            priceMultiplier /= 20
        }
        val priceDouble = ((productDetailList?.get(position)?.variants?.get(0)?.price)?.toDouble() ?: 00.00) * priceMultiplier
        val price = String.format("%.2f", priceDouble) + " " + currency

        productDetailList?.get(position)?.let {
            productDetailList?.get(position)?.let { it1 ->
                    holder.bind(productDetailList?.get(position)!!.image.src, it.title, it1.amount,
                        price)
            }
        }
        holder._bindView.scRowDeleteBtn.setOnClickListener{
            productDetailList?.get(position)?.let { it1 -> listener.checkToDelete(it1) }
        }
        holder._bindView.minusBtn.setOnClickListener {
            if(productDetailList?.get(position)?.amount!! > 1){
                productDetailList?.get(position)?.let { it.amount-- }
                holder._bindView.amount.text = productDetailList?.get(position)?.amount.toString()
                productDetailList?.get(position)?.let { it1 -> listener.update(it1) }
                productDetailList?.get(position)?.let { it1 -> listener.decrementTotalPrice(it1) }
            }
        }
        holder._bindView.plusBtn.setOnClickListener {
            productDetailList?.get(position)?.let { it.amount++ }
            holder._bindView.amount.text = productDetailList?.get(position)?.amount.toString()
            productDetailList?.get(position)?.let { it1 -> listener.update(it1) }
            productDetailList?.get(position)?.let { it1 -> listener.incrementTotalPrice(it1) }
        }
    }

    override fun getItemCount(): Int {
        //return shoppingCarts.size
        return productDetailList!!.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ProductDetail>) {
        productDetailList = data
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
    }
}