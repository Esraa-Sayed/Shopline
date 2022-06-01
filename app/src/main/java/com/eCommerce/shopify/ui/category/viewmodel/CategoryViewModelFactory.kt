package com.eCommerce.shopify.ui.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.category.repo.CategoryRepoInterface

class CategoryViewModelFactory (private val _repo: CategoryRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            CategoryViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("CategoryViewModel Class not found")
        }
    }
}