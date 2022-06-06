package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import retrofit2.Response

interface RemoteSource {

    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    suspend fun getUserWithEmail(userEmail:String):Response<UserData>

    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>

    suspend fun getCategoryProducts(id: Long): Response<Products>

    suspend fun getProductDetails(id: Long): Response<ProductDetails>
	
    suspend fun getCollectionWithId(vendor:String):Response<BrandProductsResponse>

    suspend fun registerCustomer(customer: CustomerResponse):Response<CustomerResponse>

    suspend fun getUserAddresses(id:Long):Response<AddressesUserModel>

    suspend fun getUserOrders(id:Long):Response<OrderModel>

    suspend fun updateUser(id: Long, customer: Customer): Response<Customer>
	
    suspend fun postOrder(order: OrderDetails):Response<OrderDetails>
}