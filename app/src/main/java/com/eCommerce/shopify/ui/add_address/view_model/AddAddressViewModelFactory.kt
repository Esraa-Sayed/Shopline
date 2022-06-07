package com.eCommerce.shopify.ui.add_address.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.search.view_model.SearchViewModel
import com.eCommerce.shopify.ui.setting.repo.AddAddressRepoInterface
import java.lang.IllegalArgumentException

class AddAddressViewModelFactory(private val _repo: AddAddressRepoInterface):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddAddressViewModel:: class.java)){
            AddAddressViewModel(_repo) as T
        }
        else{
            throw IllegalArgumentException("AddAddressViewModel class not found")
        }
    }
}