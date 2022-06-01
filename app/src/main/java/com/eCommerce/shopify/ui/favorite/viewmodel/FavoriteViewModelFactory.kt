package com.eCommerce.shopify.ui.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepoInterface

class FavoriteViewModelFactory(private val repo: FavoriteRepoInterface): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(repo) as T
        } else {
            throw IllegalArgumentException("FavoriteViewModel Class not found")
        }
    }
}