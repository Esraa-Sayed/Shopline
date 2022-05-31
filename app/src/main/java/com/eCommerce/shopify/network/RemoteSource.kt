package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
import retrofit2.Response

interface RemoteSource {

    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    suspend fun getUserWithEmail(userEmail:String):Response<UserData>

    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>

    suspend fun getUserOrders(id:Long):Response<OrderModel>

    suspend fun getUserAddresses(id:Long):Response<AddressesUserModel>

}