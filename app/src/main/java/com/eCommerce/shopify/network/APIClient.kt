package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
import com.eCommerce.shopify.model.discount.DiscountCodes
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import retrofit2.Response

class APIClient private constructor() : RemoteSource {

    companion object {
        private var instance: APIClient? = null
        fun getInstance(): APIClient {
            return instance ?: APIClient()
        }
    }

    private fun getAPIService(): APIService {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java)
    }

    override suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand> {
        return getAPIService().getSmartCollectionsBrand()
    }

    override suspend fun getUserWithEmail(userEmail: String): Response<UserData> {
        return getAPIService()
            .getUserWithEmail(userEmail)
    }

    override suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory> {
        return getAPIService().getCustomCollectionsCategory()
    }


    override suspend fun getCategoryProducts(id: Long): Response<Products> {
        return getAPIService().getCategoryProducts(id)
    }

    override suspend fun getProductDetails(id: Long): Response<ProductDetails> {
        return getAPIService().getProductDetails(id)
    }

    override suspend fun getCollectionWithId(vendor: String): Response<BrandProductsResponse> {
        return getAPIService().getCollectionWithId(vendor)
    }

    override suspend fun registerCustomer(customer: CustomerResponse): Response<CustomerResponse> {
        return getAPIService().registerCustomer(customer)
    }


    override suspend fun getUserOrders(id: Long): Response<OrderModel> {
        return getAPIService().getUserOrders(id)

    }


    override suspend fun updateUser(id: Long, customer: Customer): Response<Customer> {
        return BaseRetrofitHelper.getInstance().create(APIClient::class.java)
            .updateUser(id, customer)
    }

    override suspend fun postOrder(order: OrderDetails): Response<OrderDetails> {
        return getAPIService().postOrder(order)
    }

    override suspend fun getDiscountCodes(): Response<DiscountCodes> {
        return getAPIService().getDiscountCodes()
    }

    override suspend fun getAllProducts(): Response<Products> {
        return getAPIService().getAllProducts()
    }

    override suspend fun addAddress(
        userId: Long,
        address: PostAddress
    ): Response<AddressesUserModel> {
        return getAPIService().addAddress(userId, address)
    }

    override suspend fun deleteAddress(userId: Long, addressId: Long) {
        return getAPIService().deleteAddress(userId, addressId)
    }


    override suspend fun getUserAddresses(id: Long): Response<AddressesUserModel> {
        return getAPIService().getUserAddresses(id)
    }
}