package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET("smart_collections.json")
    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    @GET("customers.json")
    suspend fun getUserWithEmail(@Query(value = "email") email:String):Response<UserData>

    @GET("custom_collections.json")
    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>

    @GET("products.json")
    suspend fun getCollectionWithId(@Query("vendor") vendor:String):Response<BrandProductsResponse>

    @POST("customers.json")
    suspend fun registerCustomer(@Body customer: CustomerResponse): Response<CustomerResponse>
}