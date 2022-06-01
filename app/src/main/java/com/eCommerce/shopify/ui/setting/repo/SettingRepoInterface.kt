package com.eCommerce.shopify.ui.setting.repo

import android.content.Context

interface SettingRepoInterface {
    fun getIsLogin(context: Context): Boolean
    fun setIsLogin(context: Context, isLogin: Boolean)
}