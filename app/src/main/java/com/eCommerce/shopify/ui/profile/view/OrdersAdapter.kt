package com.eCommerce.shopify.ui.profile.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.ProfileOrdersRowBinding
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.utils.AppConstants

class OrdersAdapter(
    val OnOrderListner: OnOrderListner,
    val onProductListner: OnProductListner,
    val currency: String
): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    private var orders: List<Order> =listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemBinding = ProfileOrdersRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        var priceMultiplier: Double = 1.0
        if(currency != AppConstants.EGP){
            priceMultiplier = priceMultiplier/10
        }
        val priceDouble = (orders[position].total_price!!).toDouble() * priceMultiplier
        val price = String.format("%.2f", priceDouble) + " " + currency
        holder.bind(orders[position].created_at!!.split("T")[0],
            price)
        //holder.bind("25/01/2021", "145.32")
        holder._bindView.pOrdersRowCardview.setOnClickListener{
            OnOrderListner.onOrderClicked(orders[position])
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOrders(orders: List<Order>){
        this.orders = orders
        notifyDataSetChanged()
    }

    fun getOrders(): List<Order>{
        return orders
    }

    override fun getItemCount(): Int {
        if(orders.isEmpty())
            return 0
        else if(orders.size > 2)
            return 2
        return orders.size
    }

    class OrdersViewHolder(val _bindView: ProfileOrdersRowBinding)
        : RecyclerView.ViewHolder(_bindView.root) {

        fun bind(date: String, price: String){
            _bindView.pRowOrderPrice.text = price
            _bindView.pRowOrderDate.text = date
        }
    }
}