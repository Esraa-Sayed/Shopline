package com.eCommerce.shopify.ui.shopping_cart.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.eCommerce.shopify.ui.shopping_cart.repo.ShoppingCartRepoInterface

class ShoppingCartViewModel(val repo: ShoppingCartRepoInterface) : ViewModel() {

    fun getIsLogin(requireContext: Context): Boolean {
        return repo.getIsLogin(context = requireContext)
    }

    fun setIsLogin(requireContext: Context, isLogin: Boolean) {
        return repo.setIsLogin(context = requireContext, isLogin)
    }
}