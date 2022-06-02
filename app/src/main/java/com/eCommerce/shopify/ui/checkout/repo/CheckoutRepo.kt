package com.eCommerce.shopify.ui.checkout.repo

import android.util.Log
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import com.eCommerce.shopify.network.RemoteSource
import retrofit2.Response

class CheckoutRepo private constructor(private var remoteSource: RemoteSource):CheckoutRepoInterface{
    companion object {
        private var instance: CheckoutRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): CheckoutRepoInterface{
            return instance ?: CheckoutRepo(remoteSource)
        }
    }
    override suspend fun postOrder(order: Order): Response<OrderDetails> {
        Log.d("dfsfdf:", order.toString())
        val orderDetails:OrderDetails = OrderDetails(order)
        return remoteSource.postOrder(orderDetails)
    }
}