package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.UserData
import retrofit2.Response
import java.util.*

interface SettingRepoInterface {
    fun getIsLogin(context: Context): Boolean
    fun setIsLogin(context: Context, isLogin: Boolean)
    fun getUserName(context: Context): String
    fun getUserEmail(context: Context): String
    suspend fun getUserDataWithEmail(email:String): Response<UserData>
    suspend fun updateCurrency(context: Context, currency: String)
    suspend fun updateCustomerToApi(context: Context, customer: Customer): Response<Customer>
    fun getCurrencyFromSharedPref(context: Context): String
    fun setCurrencyToSharedPref(context: Context, value: String)
}