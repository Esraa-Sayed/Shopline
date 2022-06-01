package com.eCommerce.shopify.ui.addresses.repo

import android.content.Context
import com.eCommerce.shopify.model.AddressesUserModel
import retrofit2.Response

interface AddressesRepoInterface {
    suspend fun getUserAddressesWithId(userId:Long): Response<AddressesUserModel>
    fun getUserIdFromSharedPref(context: Context, key: String, defaultValue: Long):Long
}