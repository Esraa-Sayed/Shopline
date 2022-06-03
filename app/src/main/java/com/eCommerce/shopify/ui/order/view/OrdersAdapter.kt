package com.eCommerce.shopify.ui.order.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.ProfileOrdersRowBinding
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.utils.AppConstants
import kotlin.math.pow

class OrdersAdapter(private val context: Context, private var  orders:List<Order>,private var userCurrency: String,private var onOrderRowClicked:OnOrderRowClicked): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersViewHolder {
        val view = ProfileOrdersRowBinding.inflate(LayoutInflater.from(context) , parent,false)
        return OrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val order = orders[position]
        if (userCurrency == AppConstants.EGP)
            ("${order.total_price} ${AppConstants.EGP}").also { holder.viewBinding.pRowOrderPrice.text = it }
        else
        {
           val price = order.total_price?.toDouble()?.div(20)
           ("${price} $").also { holder.viewBinding.pRowOrderPrice.text = it }
        }

        holder.viewBinding.pRowOrderDate.text = order.created_at!!.split("T")[0]

        holder.viewBinding.pOrdersRowCardview.setOnClickListener{
            Log.e("TAG", "onBindViewHolder: YEss")
            onOrderRowClicked.onRowClickedListener(order)
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun updateData(orders:List<Order>){
        this.orders = orders
        notifyDataSetChanged()
    }
    class OrdersViewHolder(var viewBinding:ProfileOrdersRowBinding):RecyclerView.ViewHolder(viewBinding.root)
}