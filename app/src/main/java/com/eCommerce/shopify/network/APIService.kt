package com.eCommerce.shopify.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.eCommerce.shopify.model.*
import retrofit2.http.Query


interface APIService {

    @GET("smart_collections.json")
    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>


    @GET("customers.json")
    suspend fun getUserWithEmail(@Query(value = "email") email:String):Response<UserData>

    @GET("custom_collections.json")
    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>


    @GET("collections/{id}/products.json")
    suspend fun getCategoryProducts(
        @Path("id") id: Long
    ): Response<Products>


    @GET("products.json")
    suspend fun getCollectionWithId(@Query("vendor") vendor:String):Response<BrandProductsResponse>

    @GET("customers/{id}/orders.json")
    suspend fun getUserOrders( @Path("id")id: Long):Response<OrderModel>
}