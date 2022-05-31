package com.eCommerce.shopify.ui.profile.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.ProfileOrdersRowBinding
import com.eCommerce.shopify.model.OrderModel

class OrdersAdapter(val OnOrderListner: OnOrderListner, val onProductListner: OnProductListner): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    var orders: List<OrderModel> =listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemBinding = ProfileOrdersRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        //holder.bind(orders[position].date, orders[position].price)
        holder.bind("25/01/2021", "145.32")
        holder._bindView.pOrdersRowCardview.setOnClickListener{
//            var orderDetailsItems: OrderDetailsItems = OrderDetailsItems(orders[position].image, orders[position].name,
//            orders[position].price, orders[position].quantity)
            OnOrderListner.onOrderClicked(orders[position])
        }

    }

    override fun getItemCount(): Int {
        return 2
    }

    class OrdersViewHolder(val _bindView: ProfileOrdersRowBinding)
        : RecyclerView.ViewHolder(_bindView.root) {

        fun bind(date: String, price: String){
            _bindView.pRowOrderPrice.text = price
            _bindView.pRowOrderDate.text = date
        }
    }
}