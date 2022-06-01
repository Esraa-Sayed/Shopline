package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref

class SettingRepo: SettingRepoInterface{

    override fun getIsLogin(context: Context): Boolean{
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return sharedPreferences.getBooleanValue(AppConstants.IS_LOGIN, false)
    }

    override fun setIsLogin(context: Context, isLogin: Boolean) {
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        sharedPreferences.setValue(AppConstants.IS_LOGIN, false)
    }
}