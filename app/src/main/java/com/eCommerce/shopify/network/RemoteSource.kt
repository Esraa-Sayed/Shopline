package com.eCommerce.shopify.network

import android.content.Context
import com.eCommerce.shopify.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

interface RemoteSource {

    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    suspend fun getUserWithEmail(userEmail:String):Response<UserData>

    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>

    suspend fun getCategoryProducts(id: Long): Response<Products>

    suspend fun getProductDetails(id: Long): Response<ProductDetails>
	
    suspend fun getCollectionWithId(vendor:String):Response<BrandProductsResponse>

    suspend fun registerCustomer(customer:CustomerResponse):Response<CustomerResponse>

    suspend fun getUserAddresses(id:Long):Response<AddressesUserModel>
	
    suspend fun getUserOrders(id:Long):Response<OrderModel>

    suspend fun updateUser(id: Long, customer: Customer): Response<Customer>


}