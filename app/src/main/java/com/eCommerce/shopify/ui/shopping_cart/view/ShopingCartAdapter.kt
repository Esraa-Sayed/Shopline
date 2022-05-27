package com.eCommerce.shopify.ui.shopping_cart.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.model.CartItem

class ShopingCartAdapter(var listner: Listner) : RecyclerView.Adapter<ShopingCartAdapter.ShopingCartViewHolder>() {

    var shoppingCarts: MutableList<CartItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopingCartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_cart_row, parent, false)

        return ShopingCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopingCartViewHolder, position: Int) {
//        holder.vshoppingCarts[position].name
    }

    override fun getItemCount(): Int {
        return shoppingCarts.size
    }

    class ShopingCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val binding = itemView.roo
    }
}