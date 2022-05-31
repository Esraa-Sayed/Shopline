package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.model.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("smart_collections.json")
    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    @GET("customers.json")
    suspend fun getUserWithEmail(@Query(value = "email") email:String):Response<UserData>
}