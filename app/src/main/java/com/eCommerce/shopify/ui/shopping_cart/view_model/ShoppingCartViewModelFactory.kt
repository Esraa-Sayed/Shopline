package com.eCommerce.shopify.ui.shopping_cart.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.shopping_cart.repo.ShoppingCartRepoInterface

class ShoppingCartViewModelFactory(private val _repo: ShoppingCartRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            ShoppingCartViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("ShoppingCartViewModel Class not found")
        }
    }
}