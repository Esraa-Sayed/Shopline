package com.eCommerce.shopify.ui.profile.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.favorite.LocalSourceInterface
import com.eCommerce.shopify.model.OrderModel
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class ProfileRepo(private val remoteSource: RemoteSource, private val localSource: LocalSourceInterface): ProfileRepoInterface {

    companion object {
        private var instance: ProfileRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource, localSource: LocalSourceInterface): ProfileRepoInterface {
            return instance ?: ProfileRepo(remoteSource, localSource)
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
    override fun getAllFavorites(): LiveData<List<Product>> {
        return localSource.getAllFavorites()
    }

    override fun insertToFavorite(product: Product) {
        localSource.insertToFavorite(product)
    }

    override fun deleteFromFavorite(product: Product) {
        localSource.deleteFromFavorite(product)
    }
    override fun getUserName(context: Context): String {
        val sharedPreferences: AppSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return sharedPreferences.getStringValue(AppConstants.USER_NAME, "NoName")
    }

}