package com.eCommerce.shopify.ui.productdetails.repo

import android.content.Context
import com.eCommerce.shopify.model.ProductDetails
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class ProductDetailsRepo private constructor(
    private var remoteSource: RemoteSource
) : ProductDetailsRepoInterface {

    companion object {
        private var instance: ProductDetailsRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): ProductDetailsRepoInterface {
            return instance ?: ProductDetailsRepo(remoteSource)
        }
    }

    override suspend fun getCurrencyWithUserEmail(context: Context): String {
        val currentCurrency: String
        val userEmail =
            AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getStringValue(
                AppConstants.USER_EMAIL, "")
        currentCurrency = if (userEmail.isBlank() || userEmail.isEmpty()) {
            "EGP"
        } else {
            val userWithEmail = remoteSource.getUserWithEmail(userEmail)
            userWithEmail.body()?.customers?.get(0)?.currency.toString()
        }

        return currentCurrency
    }

    override suspend fun getProductDetails(id: Long): Response<ProductDetails> {
        return remoteSource.getProductDetails(id)
    }
}