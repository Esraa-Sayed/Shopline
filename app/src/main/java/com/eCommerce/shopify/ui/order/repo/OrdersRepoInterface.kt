package com.eCommerce.shopify.ui.order.repo

import android.content.Context
import com.eCommerce.shopify.model.OrderModel
import retrofit2.Response

interface OrdersRepoInterface {
    suspend fun getUserOrdersWithId(userId:Long): Response<OrderModel>
    fun getUserIdFromSharedPref(context: Context,key: String, defaultValue: Long):Long
    fun getCurrency(context: Context): String
}