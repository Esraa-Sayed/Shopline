package com.eCommerce.shopify.ui.profile.repo

import android.content.Context
import com.eCommerce.shopify.model.OrderModel
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.ui.order.repo.OrdersRepo
import com.eCommerce.shopify.ui.order.repo.OrdersRepoInterface
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class ProfileRepo(private var remoteSource: RemoteSource): ProfileRepoInterface {

    companion object {
        private var instance: ProfileRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): ProfileRepoInterface {
            return instance ?: ProfileRepo(remoteSource)
        }
    }
    override suspend fun getUserOrdersWithId(userId: Long): Response<OrderModel> {
        return remoteSource.getUserOrders(userId)
    }

    override fun getUserIdFromSharedPref(context:Context,key: String, defaultValue: Long): Long {
        val fileSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return fileSharedPref.getLongValue(AppConstants.USER_ID, defaultValue)
    }

    override fun getIsLogin(context: Context): Boolean{
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return sharedPreferences.getBooleanValue(AppConstants.IS_LOGIN, false)
    }

    override fun setIsLogin(context: Context, isLogin: Boolean) {
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        sharedPreferences.setValue(AppConstants.IS_LOGIN, false)
    }

}