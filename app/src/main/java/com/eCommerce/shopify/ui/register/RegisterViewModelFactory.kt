package com.eCommerce.shopify.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModel

class RegisterViewModelFactory(private val repo:RegisterRepoInterface):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(repo) as T
        } else {
            throw IllegalArgumentException("RegisterViewModel Class not found")
        }
    }
}