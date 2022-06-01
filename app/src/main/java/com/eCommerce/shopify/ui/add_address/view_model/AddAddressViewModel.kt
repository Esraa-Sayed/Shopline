package com.eCommerce.shopify.ui.add_address.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.eCommerce.shopify.ui.setting.repo.AddAddressRepoInterface

class AddAddressViewModel : ViewModel() {
    private lateinit var repo: AddAddressRepoInterface
    fun setRepo(repo: AddAddressRepoInterface) {
        this.repo = repo
    }
}