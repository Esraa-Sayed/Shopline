package com.eCommerce.shopify.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.main.repo.MainRepoInterface

class MainViewModelFactory (private val _repo: MainRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("MainViewModel Class not found")
        }
    }
}