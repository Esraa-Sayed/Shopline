package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("smart_collections.json")
    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    @GET("products.json")
    suspend fun getCollectionWithId(@Query("vendor") vendor:String):Response<BrandProductsResponse>
}