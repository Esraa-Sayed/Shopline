package com.eCommerce.shopify.ui.brandproducts.repo

import com.eCommerce.shopify.model.BrandCollectionResponse
import retrofit2.Response

interface BrandProductsRepositoryInterface {
    suspend fun getCollectionWithId(collectionId:Long): Response<BrandCollectionResponse>
}