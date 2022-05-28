package com.eCommerce.shopify.ui.order.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.ProfileOrdersRowBinding
import com.eCommerce.shopify.model.OrderModel

class OrdersAdapter(private val context: Context,private var  orders:List<OrderModel>): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    //private lateinit var itemBinding:
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersViewHolder {
        val view = ProfileOrdersRowBinding.inflate(LayoutInflater.from(context) , parent,false)
        return OrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val order = orders[position]
        holder.viewBinding.pRowOrderDate.text = order.date
        holder.viewBinding.pRowOrderPrice.text = order.price
        holder.viewBinding.pOrdersRowCardview.setOnClickListener{
            Log.e("TAG", "onBindViewHolder: YEss")
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
    fun updateData(orders:List<OrderModel>){
        this.orders = orders
    }
    class OrdersViewHolder(var viewBinding:ProfileOrdersRowBinding):RecyclerView.ViewHolder(viewBinding.root) {

    }
}