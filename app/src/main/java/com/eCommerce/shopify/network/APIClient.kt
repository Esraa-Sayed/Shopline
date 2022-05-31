package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response

class APIClient private constructor(): RemoteSource {

    companion object{
        private var instance: APIClient? = null
        fun getInstance(): APIClient {
            return instance ?: APIClient()
        }
    }

    override suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getSmartCollectionsBrand()
    }

    override suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getCustomCollectionsCategory()
    }

    override suspend fun getCategoryProducts(id: Long): Response<Products> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getCategoryProducts(id)
    }
}