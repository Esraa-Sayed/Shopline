package com.eCommerce.shopify.ui.setting.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.eCommerce.shopify.ui.setting.repo.AddAddressRepoInterface
import com.eCommerce.shopify.ui.setting.repo.SettingRepo
import com.eCommerce.shopify.ui.setting.repo.SettingRepoInterface

class SettingViewModel : ViewModel() {

    private lateinit var repo: SettingRepoInterface
    fun setRepo(repo: SettingRepoInterface) {
        this.repo = repo
    }

    fun getIsLogin(requireContext: Context): Boolean {
        return repo.getIsLogin(context = requireContext)
    }

    fun setIsLogin(requireContext: Context, isLogin: Boolean) {
        return repo.setIsLogin(context = requireContext, isLogin)
    }
}