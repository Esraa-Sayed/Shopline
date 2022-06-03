package com.eCommerce.shopify.ui.order.repo

import android.content.Context
import com.eCommerce.shopify.model.OrderModel
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class OrdersRepo private constructor(
private var remoteSource: RemoteSource
):OrdersRepoInterface{
    companion object {
        private var instance: OrdersRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): OrdersRepoInterface {
            return instance ?: OrdersRepo(remoteSource)
        }
    }
    override suspend fun getUserOrdersWithId(userId: Long): Response<OrderModel> {
       return remoteSource.getUserOrders(userId)
    }

    override fun getUserIdFromSharedPref(context:Context,key: String, defaultValue: Long): Long {
        val fileSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return fileSharedPref.getLongValue(AppConstants.USER_ID, defaultValue)
    }
    override fun getCurrency(context: Context): String {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getStringValue(AppConstants.CURRENCY,
            AppConstants.EGP
        )
    }
}