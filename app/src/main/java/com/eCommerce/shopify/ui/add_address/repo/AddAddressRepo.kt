package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.model.Addresse
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.PostAddress
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class AddAddressRepo(private val remoteSource: RemoteSource): AddAddressRepoInterface{
    companion object {
        private var instance: AddAddressRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): AddAddressRepoInterface {
            return instance ?: AddAddressRepo(remoteSource)
        }
    }
    override suspend fun addAddress(id: Long, addresse: PostAddress): Response<PostAddress> {
        return remoteSource.addAddress(id, addresse)
    }
    override fun getUserIdFromSharedPref(context:Context, defaultValue: Long): Long {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getLongValue(AppConstants.USER_ID, defaultValue)
    }

}