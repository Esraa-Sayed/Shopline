package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.BrandCollectionResponse
import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response

interface RemoteSource {

    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>
    suspend fun getCollectionWithId(collectionId:Long):Response<BrandCollectionResponse>
}