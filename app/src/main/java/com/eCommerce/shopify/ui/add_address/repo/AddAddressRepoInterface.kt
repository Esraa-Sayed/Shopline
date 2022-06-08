package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.model.Addresse
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.PostAddress
import retrofit2.Response

interface AddAddressRepoInterface {
    suspend fun addAddress(id: Long, addresse: PostAddress): Response<PostAddress>
    fun getUserIdFromSharedPref(context:Context, defaultValue: Long): Long
}