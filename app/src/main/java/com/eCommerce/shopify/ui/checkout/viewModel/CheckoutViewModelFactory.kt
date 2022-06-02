package com.eCommerce.shopify.ui.checkout.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.checkout.repo.CheckoutRepoInterface

class CheckoutViewModelFactory (private val _repo: CheckoutRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            CheckoutViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("CategoryViewModel Class not found")
        }
    }
}