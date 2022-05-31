package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response
import retrofit2.http.Path

interface RemoteSource {

    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>
    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>
    suspend fun getCategoryProducts(id: Long): Response<Products>
}