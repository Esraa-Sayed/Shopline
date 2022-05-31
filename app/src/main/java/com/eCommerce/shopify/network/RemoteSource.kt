package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.model.UserData
import retrofit2.Response

interface RemoteSource {

    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>
    suspend fun getUserWithEmail(userEmail:String):Response<UserData>
}