package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.model.UserData
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


    override suspend fun getUserWithEmail(userEmail: String): Response<UserData> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java)
            .getUserWithEmail(userEmail)
    }
    override suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getCustomCollectionsCategory()
    }
    override suspend fun getCollectionWithId(vendor:String): Response<BrandProductsResponse> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getCollectionWithId(vendor)
    }

}