package com.eCommerce.shopify.ui.productdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.productdetails.repo.ProductDetailsRepoInterface

class ProductDetailsViewModelFactory (private val _repo: ProductDetailsRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            ProductDetailsViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("ProductDetailsViewModel Class not found")
        }
    }
}