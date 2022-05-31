package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.BrandCollectionResponse
import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("smart_collections.json")
    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    @GET("collections/{collectionId}/products.json")
    suspend fun getCollectionWithId(@Path("collectionId") collectionId:Long):Response<BrandCollectionResponse>
}