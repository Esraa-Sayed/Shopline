package com.eCommerce.shopify.ui.register

import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.network.RemoteSource
import retrofit2.Response

class RegisterRepo private constructor(private val remoteSource:RemoteSource):RegisterRepoInterface{

    companion object{
        private val instance:RegisterRepoInterface? = null
        fun getInstance(remoteSource:RemoteSource):RegisterRepoInterface{
            return instance?:RegisterRepo(remoteSource)
        }
    }

    override suspend fun registerCustomer(customer: CustomerResponse): Response<CustomerResponse> {
        return remoteSource.registerCustomer(customer)
    }

}