package com.eCommerce.shopify.ui.orderDetails.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.OrdersDetailsRowBinding
import com.eCommerce.shopify.model.orderDetails.LineItem
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import com.eCommerce.shopify.utils.AppConstants

class OrderDetailsAdapter(val context: Context,private var userCurrency: String, var itemsInOrder: Array<LineItem>):
    RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
       val view = OrdersDetailsRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return OrderDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
       val item = itemsInOrder[position]
        val words = item.name?.lowercase()?.split(" ")
        var orderName = ""
        words?.forEach { word ->
            orderName += word.replaceFirstChar { it.uppercase() } + " "
        }
        holder.binding.brandOrderName.text = orderName

        if (userCurrency == AppConstants.EGP)
            ("${item.price} ${AppConstants.EGP}").also {  holder.binding.brandOrderPrice.text = it }
        else
        {
            val price = item.price?.toDouble()?.div(20)
            ("${price} $").also {  holder.binding.brandOrderPrice.text = it }
        }
        holder.binding.brandOrderQuantity.text = item.fulfillable_quantity.toString()
    }
    fun updateData(itemsInOrder:Array<LineItem>){
        this.itemsInOrder = itemsInOrder
    }
    override fun getItemCount(): Int {
       return itemsInOrder.size
    }
    class OrderDetailsViewHolder(var binding: OrdersDetailsRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

}