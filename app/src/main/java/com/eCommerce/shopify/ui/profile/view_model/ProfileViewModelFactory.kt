package com.eCommerce.shopify.ui.profile.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.ui.profile.repo.ProfileRepoInterface

class ProfileViewModelFactory(private val _repo: ProfileRepoInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("ProfileViewModel Class not found")
        }
    }
}