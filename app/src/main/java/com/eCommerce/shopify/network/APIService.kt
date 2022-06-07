package com.eCommerce.shopify.network

import retrofit2.http.*
import retrofit2.Response
import com.eCommerce.shopify.model.*
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.model.discount.DiscountCodes
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import retrofit2.http.Query


interface APIService {

    @GET("smart_collections.json")
    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>

    @GET("customers.json")
    suspend fun getUserWithEmail(@Query(value = "email") email:String):Response<UserData>

    @GET("custom_collections.json")
    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>

    @GET("collections/{id}/products.json")
    suspend fun getCategoryProducts(
        @Path("id") id: Long
    ): Response<Products>

    @GET("products.json")
    suspend fun getAllProducts(): Response<Products>

    @GET("products/{id}.json")
    suspend fun getProductDetails(
        @Path("id") id: Long
    ): Response<ProductDetails>

    @GET("products.json")
    suspend fun getCollectionWithId(@Query("vendor") vendor:String):Response<BrandProductsResponse>

    @POST("customers.json")
    suspend fun registerCustomer(@Body customer: CustomerResponse): Response<CustomerResponse>

    @GET("customers/{id}/orders.json")
    suspend fun getUserOrders( @Path("id")id: Long):Response<OrderModel>

    @GET("customers/{id}/addresses.json")
    suspend fun getUserAddresses(@Path("id")id:Long):Response<AddressesUserModel>

    //@Headers("Content-Type: application/json")
    //, @Body customer: Customer
    @PUT("customers/{id}.json")
    suspend fun updateUser(@Path("id")id:Long): Response<Customer>

    @POST("orders.json")
    suspend fun postOrder(@Body order:OrderDetails):Response<OrderDetails>


    @GET("price_rules/1027349774508/discount_codes.json")
    suspend fun getDiscountCodes():Response<DiscountCodes>

    //@Headers("Content-Type: application/json")
    //, @Body body: Customer
    @PUT("customers/{id}.json")
    suspend fun addAddress(@Path("id") id: Long): Response<Customer>

}