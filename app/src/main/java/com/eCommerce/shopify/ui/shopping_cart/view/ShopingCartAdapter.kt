package com.eCommerce.shopify.ui.shopping_cart.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.ShoppingCartRowBinding
import com.eCommerce.shopify.model.CartItem

class ShopingCartAdapter(var listner: Listner) : RecyclerView.Adapter<ShopingCartAdapter.ShoppingCartViewHolder>() {

    var shoppingCarts: MutableList<CartItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val itemBinding = ShoppingCartRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
//        holder.bind(shoppingCarts[position].imageUrl, shoppingCarts[position].name, shoppingCarts[position].amount,
//                shoppingCarts[position].price)
        holder.bind("kkj", "My shooping card", 10,
                145.5)

    }
//    fun bindy(imageUrl: String, name: String, amount: Int, price: Double, holder: ShoppingCartViewHolder){
//        if (imageUrl !== null) {
//            Glide.with(holder.itemView.context)
//                .load(imageUrl)
//                .into(holder._bindView.scRowItemImage)
//        } else {
//            holder._bindView.scRowItemImage.setImageResource(R.drawable.ic_launcher_background)
//        }
//        holder._bindView.scRowItemName.text = name
//        holder._bindView.scRowItemPrice.text = price.toString()
//        holder._bindView.scRowItemCount.text = amount.toString()
//    }

    override fun getItemCount(): Int {
        //return shoppingCarts.size
        return 10
    }

    class ShoppingCartViewHolder(val _bindView: ShoppingCartRowBinding)
        : RecyclerView.ViewHolder(_bindView.root) {

        fun bind(imageUrl: String, name: String, amount: Int, price: Double){
            if (imageUrl !== null) {
                Glide.with(_bindView.root.context)
                    .load(imageUrl)
                    .into(_bindView.scRowItemImage)
            } else {
                _bindView.scRowItemImage.setImageResource(R.drawable.ic_launcher_background)
            }
            _bindView.scRowItemName.text = name
            _bindView.scRowItemPrice.text = price.toString()
            _bindView.scRowItemCount.text = amount.toString()
        }
        fun bindAmount(amount: Int){
            _bindView.scRowItemCount.text = amount.toString()
        }
    }
}