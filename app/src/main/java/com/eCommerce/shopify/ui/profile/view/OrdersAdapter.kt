package com.eCommerce.shopify.ui.profile.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.ProfileOrdersRowBinding

class OrdersAdapter: RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    //var orders: List<Order> =listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemBinding = ProfileOrdersRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
//        holder.bind(orders[position].date, orders[position].price)
        holder.bind("25/01/2021", 145.32)

    }

    override fun getItemCount(): Int {
        return 2
    }

    class OrdersViewHolder(val _bindView: ProfileOrdersRowBinding)
        : RecyclerView.ViewHolder(_bindView.root) {

        fun bind(date: String, price: Double){
            _bindView.pRowOrderPrice.text = price.toString()
            _bindView.pRowOrderDate.text = date
        }
    }
}