package com.eCommerce.shopify.ui.orderDetails.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.eCommerce.shopify.ui.order.repo.OrdersRepoInterface

class OrdersDetailsViewModel(var _repo: OrdersRepoInterface) : ViewModel() {
    fun getCurrency(context: Context): String{
        return  _repo.getCurrency(context)
    }
}