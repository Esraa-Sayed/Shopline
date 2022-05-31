package com.eCommerce.shopify.ui.brandproducts.repo

import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.network.RemoteSource
import retrofit2.Response

class BrandProductsRepository private constructor(
    private var remoteSource: RemoteSource
):BrandProductsRepositoryInterface{

    companion object{
        private var instance:BrandProductsRepositoryInterface? = null
        fun getInstance(remoteSource: RemoteSource):BrandProductsRepositoryInterface{
            return instance?:BrandProductsRepository(remoteSource)
        }
    }

    override suspend fun getCollectionWithId(vendor:String): Response<BrandProductsResponse> {
        return remoteSource.getCollectionWithId(vendor)
    }
}