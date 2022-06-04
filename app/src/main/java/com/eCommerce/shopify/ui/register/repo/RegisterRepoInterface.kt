package com.eCommerce.shopify.ui.register.repo

import android.content.Context
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.model.UserData
import retrofit2.Response

interface RegisterRepoInterface {
    suspend fun registerCustomer(customer: CustomerResponse): Response<CustomerResponse>
    fun saveDataInSharedPref(context: Context, email: String, userId: Long, userName: String)
}