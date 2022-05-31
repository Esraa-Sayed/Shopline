package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response

interface RemoteSource {

    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>
    suspend fun getCollectionWithId(vendor:String):Response<BrandProductsResponse>
}