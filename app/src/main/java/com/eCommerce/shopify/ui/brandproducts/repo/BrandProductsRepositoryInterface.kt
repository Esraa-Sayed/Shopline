package com.eCommerce.shopify.ui.brandproducts.repo

import com.eCommerce.shopify.model.BrandProductsResponse
import retrofit2.Response

interface BrandProductsRepositoryInterface {
    suspend fun getCollectionWithId(vendor:String): Response<BrandProductsResponse>
}