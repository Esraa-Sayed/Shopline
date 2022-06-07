package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.ui.search.repo.SearchRepo
import com.eCommerce.shopify.ui.search.repo.SearchRepoInterface
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
    override suspend fun addAddress(id: Long): Response<Customer> {
        return remoteSource.addAddress(id)
    }

}