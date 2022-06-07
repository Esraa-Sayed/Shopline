package com.eCommerce.shopify.ui.setting.repo

import android.content.Context
import com.eCommerce.shopify.model.Customer
import retrofit2.Response

interface AddAddressRepoInterface {
    suspend fun addAddress(id: Long): Response<Customer>
}