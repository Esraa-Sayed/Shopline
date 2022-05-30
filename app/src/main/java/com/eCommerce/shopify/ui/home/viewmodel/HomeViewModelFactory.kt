package com.eCommerce.shopify.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.home.repo.HomeRepoInterface

class HomeViewModelFactory (private val _repo: HomeRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("HomeViewModel Class not found")
        }
    }
}