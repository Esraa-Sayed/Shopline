package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class SettingRepo(val remoteSource: RemoteSource): SettingRepoInterface{

    override fun getIsLogin(context: Context): Boolean{
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return sharedPreferences.getBooleanValue(AppConstants.IS_LOGIN, false)
    }

    override fun setIsLogin(context: Context, isLogin: Boolean) {
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        sharedPreferences.setValue(AppConstants.IS_LOGIN, false)
    }

    override fun getUserName(context: Context): String {
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return sharedPreferences.getStringValue(AppConstants.USER_NAME, "NoName")
    }

    override fun getUserEmail(context: Context): String {
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return sharedPreferences.getStringValue(AppConstants.USER_EMAIL, "NoEmail")
    }
    override suspend fun getUserDataWithEmail(email:String): Response<UserData> {
        return remoteSource.getUserWithEmail(email)
    }

}