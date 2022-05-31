package com.eCommerce.shopify.ui.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.product.repo.ProductRepoInterface

class ProductViewModelFactory (private val _repo: ProductRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            ProductViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("ProductViewModel Class not found")
        }
    }
}