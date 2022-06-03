package com.eCommerce.shopify.ui.shopping_cart.repo

import android.content.Context
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref

interface ShoppingCartRepoInterface {
    fun getIsLogin(context: Context): Boolean

    fun setIsLogin(context: Context, isLogin: Boolean)
}