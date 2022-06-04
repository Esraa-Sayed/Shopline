package com.eCommerce.shopify.ui.brandproducts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.brandproducts.repo.BrandProductsRepositoryInterface
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepoInterface

class BrandProductsViewModelFactory(private val repo:BrandProductsRepositoryInterface):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BrandProductsViewModel::class.java)) {
            BrandProductsViewModel(repo) as T
        } else {
            throw IllegalArgumentException("BrandProductsViewModel Class not found")
        }
    }
}