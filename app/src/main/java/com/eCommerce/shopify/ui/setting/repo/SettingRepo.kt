package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class SettingRepo(val remoteSource: RemoteSource): SettingRepoInterface{

    override fun getIsLogin(context: Context): Boolean{
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getBooleanValue(AppConstants.IS_LOGIN, false)
    }

    override fun setIsLogin(context: Context, isLogin: Boolean) {
        AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).setValue(AppConstants.IS_LOGIN, false)
    }

    override fun getUserName(context: Context): String {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getStringValue(AppConstants.USER_NAME, "NoName")
    }

    override fun getUserEmail(context: Context): String {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getStringValue(AppConstants.USER_EMAIL, "NoEmail")
    }
    fun getUserId(context: Context): Long {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getLongValue(AppConstants.USER_ID, 0)
    }
    override suspend fun getUserDataWithEmail(email:String): Response<UserData> {
        return remoteSource.getUserWithEmail(email)
    }

    override fun setCurrencyToSharedPref(context: Context, value: String){
        AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).setValue(AppConstants.CURRENCY, value)
    }
    override suspend fun updateCurrency(context: Context, currency: String){
        setCurrencyToSharedPref(context, currency)
    }

    override fun getCurrencyFromSharedPref(context: Context): String {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getStringValue(AppConstants.CURRENCY, AppConstants.EGP)
    }

    override suspend fun updateCustomerToApi(
        context: Context,
        customer: Customer
    ): Response<Customer> {
        return remoteSource.updateUser(getUserId(context), customer = customer)
    }

}