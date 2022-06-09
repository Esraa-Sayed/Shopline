package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.model.discount.DiscountCodes
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import com.eCommerce.shopify.model.PostAddress
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

    suspend fun getDiscountCodes():Response<DiscountCodes>

    suspend fun getAllProducts(): Response<Products>

    suspend fun addAddress(userId: Long, address: PostAddress): Response<AddressesUserModel>

    suspend fun deleteAddress(userId: Long, addressId: Long)

}