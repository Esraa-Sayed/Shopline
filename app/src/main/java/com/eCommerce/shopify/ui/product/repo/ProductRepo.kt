package com.eCommerce.shopify.ui.product.repo

import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.network.RemoteSource
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