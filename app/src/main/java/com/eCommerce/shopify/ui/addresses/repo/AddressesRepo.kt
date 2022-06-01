package com.eCommerce.shopify.ui.addresses.repo

import android.content.Context
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class AddressesRepo private constructor(private var remoteSource: RemoteSource):AddressesRepoInterface{
    companion object{
        private var instance:AddressesRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource):AddressesRepoInterface{
            return instance ?: AddressesRepo(remoteSource)
        }
    }
    override suspend fun getUserAddressesWithId(userId: Long): Response<AddressesUserModel> {
        return remoteSource.getUserAddresses(userId)
    }

    override fun getUserIdFromSharedPref(context: Context, key: String, defaultValue: Long): Long {
        val fileSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        return fileSharedPref.getLongValue(AppConstants.USER_ID, defaultValue)
    }

}