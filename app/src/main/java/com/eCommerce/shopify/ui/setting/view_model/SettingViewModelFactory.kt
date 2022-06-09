package com.eCommerce.shopify.ui.setting.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.setting.repo.SettingRepoInterface

class SettingViewModelFactory (private val _repo: SettingRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            SettingViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("SettingViewModel Class not found")
        }
    }
}