package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
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


    override suspend fun getCategoryProducts(id: Long): Response<Products> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java)
            .getCategoryProducts(id)
    }

    override suspend fun getCollectionWithId(vendor:String): Response<BrandProductsResponse> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getCollectionWithId(vendor)
    }

    override suspend fun registerCustomer(customer: CustomerResponse): Response<CustomerResponse> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).registerCustomer(customer)
    }




    override suspend fun getUserOrders(id: Long): Response<OrderModel> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getUserOrders(id)

    }

    override suspend fun getUserAddresses(id: Long): Response<AddressesUserModel> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getUserAddresses(id)
    }
}