package com.eCommerce.shopify.ui.profile.repo

import android.content.Context
import com.eCommerce.shopify.model.OrderModel
import retrofit2.Response

interface ProfileRepoInterface {
    suspend fun getUserOrdersWithId(userId:Long): Response<OrderModel>
    fun getUserIdFromSharedPref(context: Context,key: String, defaultValue: Long):Long
    fun getIsLogin(context: Context): Boolean
    fun setIsLogin(context: Context, isLogin: Boolean)
}