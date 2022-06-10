package com.eCommerce.shopify.ui.checkout.repo

import android.content.Context
import android.util.Log
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.discount.DiscountCodes
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class CheckoutRepo private constructor(
    private var remoteSource: RemoteSource,
    private var shoppingCartLocalSource: ShoppingCartLocalSource
):CheckoutRepoInterface{
    companion object {
        private var instance: CheckoutRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource, shoppingCartLocalSource: ShoppingCartLocalSource): CheckoutRepoInterface{
            return instance ?: CheckoutRepo(remoteSource, shoppingCartLocalSource)
        }
    }
    override suspend fun postOrder(order: Order): Response<OrderDetails> {
        val orderDetails = OrderDetails(order)
        return remoteSource.postOrder(orderDetails)
    }

    override suspend fun getDiscountCodes(): Response<DiscountCodes> {
        return remoteSource.getDiscountCodes()
    }
    override fun getCurrency(context: Context): String {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getStringValue(
            AppConstants.CURRENCY,
            AppConstants.EGP
        )
    }

    override fun getUserID(context: Context): Long {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getLongValue(
            AppConstants.USER_ID,
            0
        )
    }

    override fun deleteCheckOutList(productDetail: Array<ProductDetail>) {
        for (product in productDetail) {
            shoppingCartLocalSource.deleteProductFromShoppingCart(product)
        }
    }
    override suspend fun getUserAddressesWithId(userId: Long): Response<AddressesUserModel> {
        return remoteSource.getUserAddresses(userId)
    }
}