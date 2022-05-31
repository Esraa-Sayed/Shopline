package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("smart_collections.json")
    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    @GET("custom_collections.json")
    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>

    @GET("collections/{id}/products.json")
    suspend fun getCategoryProducts(
        @Path("id") id: Long
    ): Response<Products>
}