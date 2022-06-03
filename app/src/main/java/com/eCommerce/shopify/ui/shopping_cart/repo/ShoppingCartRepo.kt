package com.eCommerce.shopify.ui.shopping_cart.repo

import android.content.Context
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref

class ShoppingCartRepo: ShoppingCartRepoInterface {
    override fun getIsLogin(context: Context): Boolean{
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getBooleanValue(AppConstants.IS_LOGIN, false)
    }

    override fun setIsLogin(context: Context, isLogin: Boolean) {
        AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).setValue(AppConstants.IS_LOGIN, false)
    }
}