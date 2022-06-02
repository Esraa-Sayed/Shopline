package com.eCommerce.shopify.ui.profile.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.ProfileOrdersRowBinding
import com.eCommerce.shopify.model.orderDetails.Order

class OrdersAdapter(val OnOrderListner: OnOrderListner, val onProductListner: OnProductListner): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    private var orders: List<Order> =listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemBinding = ProfileOrdersRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(orders[position].created_at!!.split("T")[0], orders[position].total_price!!)
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