package com.eCommerce.shopify.ui.shopping_cart.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.ui.shopping_cart.repo.ShoppingCartRepoInterface

class ShoppingCartViewModelFactory(private val context: Context, private val _repo: ShoppingCartRepoInterface, private val products: MutableList<ProductDetail>): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            ShoppingCartViewModel(context, _repo, products = products) as T
        } else {
            throw IllegalArgumentException("ShoppingCartViewModel Class not found")
        }
    }
}