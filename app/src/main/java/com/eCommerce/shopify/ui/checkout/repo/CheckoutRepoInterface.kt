package com.eCommerce.shopify.ui.checkout.repo
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import retrofit2.Response

interface CheckoutRepoInterface {
    suspend fun postOrder(order: Order): Response<OrderDetails>
}