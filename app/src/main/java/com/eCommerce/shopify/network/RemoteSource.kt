package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
import retrofit2.Response
import retrofit2.http.Path

interface RemoteSource {

    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    suspend fun getUserWithEmail(userEmail:String):Response<UserData>

    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>

    suspend fun getCategoryProducts(id: Long): Response<Products>
    suspend fun getCollectionWithId(vendor:String):Response<BrandProductsResponse>

    suspend fun getUserOrders(id:Long):Response<OrderModel>
}