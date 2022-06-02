package com.eCommerce.shopify.ui.register.repo

import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.model.UserData
import retrofit2.Response

interface RegisterRepoInterface {

    suspend fun registerCustomer(customer: CustomerResponse): Response<CustomerResponse>
}