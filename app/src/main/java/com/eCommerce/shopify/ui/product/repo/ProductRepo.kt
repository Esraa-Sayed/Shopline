package com.eCommerce.shopify.ui.product.repo

import android.content.Context
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants.PREFRENCE_File
import com.eCommerce.shopify.utils.AppConstants.USER_EMAIL
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class ProductRepo private constructor(
    private var remoteSource: RemoteSource
) : ProductRepoInterface {

    companion object {
        private var instance: ProductRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): ProductRepoInterface {
            return instance ?: ProductRepo(remoteSource)
        }
    }

    override suspend fun getCategoryProducts(id: Long): Response<Products> {
        return remoteSource.getCategoryProducts(id)
    }
}