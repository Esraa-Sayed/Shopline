package com.eCommerce.shopify.ui.checkout.repo
import android.content.Context
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.discount.DiscountCodes
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import retrofit2.Response

interface CheckoutRepoInterface {
    suspend fun postOrder(order: Order): Response<OrderDetails>
    suspend fun getDiscountCodes(): Response<DiscountCodes>
    fun getCurrency(context: Context): String
    fun getUserID(context: Context): Long
    fun deleteCheckOutList(productDetail: Array<ProductDetail>)
    suspend fun getUserAddressesWithId(userId:Long): Response<AddressesUserModel>
}