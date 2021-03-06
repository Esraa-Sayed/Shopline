package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.model.PostAddress
import retrofit2.Response

interface AddAddressRepoInterface {
    suspend fun addAddress(id: Long, address: PostAddress): Response<AddressesUserModel>
    fun getUserIdFromSharedPref(context:Context, defaultValue: Long): Long
}