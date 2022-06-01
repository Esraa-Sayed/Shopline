package com.eCommerce.shopify.ui.order.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.order.repo.OrdersRepoInterface

class OrdersViewModelFactory (private val _repo: OrdersRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            OrdersViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("HomeViewModel Class not found")
        }
    }
}