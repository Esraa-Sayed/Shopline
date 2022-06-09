package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.model.discount.DiscountCodes
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import com.eCommerce.shopify.model.PostAddress
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
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getCategoryProducts(id)
    }

    override suspend fun getProductDetails(id: Long): Response<ProductDetails> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getProductDetails(id)
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


    override suspend fun updateUser(id: Long, customer: Customer): Response<Customer> {
        return BaseRetrofitHelper.getInstance().create(APIClient::class.java).updateUser(id, customer)
		}
		
    override suspend fun postOrder(order: OrderDetails): Response<OrderDetails> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).postOrder(order)
    }

    override suspend fun getDiscountCodes(): Response<DiscountCodes> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getDiscountCodes()
    }
    override suspend fun getAllProducts(): Response<Products> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getAllProducts()
    }

    override suspend fun addAddress(userId: Long, address: PostAddress): Response<AddressesUserModel> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).addAddress(userId, address)
    }

    override suspend fun deleteAddress(userId: Long, addressId: Long) {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).deleteAddress(userId, addressId)
    }


    override suspend fun getUserAddresses(id: Long): Response<AddressesUserModel> {
        return BaseRetrofitHelper.getInstance().create(APIService::class.java).getUserAddresses(id)
    }
}