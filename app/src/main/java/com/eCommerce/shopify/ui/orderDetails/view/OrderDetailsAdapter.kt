package com.eCommerce.shopify.ui.orderDetails.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.OrdersDetailsRowBinding
import com.eCommerce.shopify.model.OrderDetailsItems
import com.eCommerce.shopify.model.OrderModel

class OrderDetailsAdapter(val context: Context,var itemsInOrder:List<OrderDetailsItems>):
    RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
       val view = OrdersDetailsRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return OrderDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
       val item = itemsInOrder[position]
       // holder.binding.imageView
        holder.binding.brandOrderName.text = item.name
        holder.binding.brandOrderPrice.text = item.price
        holder.binding.brandOrderQuantity.text = item.quantity
    }
    fun updateData(itemsInOrder:List<OrderDetailsItems>){
        this.itemsInOrder = itemsInOrder
    }
    override fun getItemCount(): Int {
       return itemsInOrder.size
    }
    class OrderDetailsViewHolder(var binding: OrdersDetailsRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

}