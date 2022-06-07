package com.eCommerce.shopify.ui.search.repo

import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.ui.product.repo.ProductRepo
import com.eCommerce.shopify.ui.product.repo.ProductRepoInterface
import retrofit2.Response

class SearchRepo(val remoteSource: RemoteSource): SearchRepoInterface {

    companion object {
        private var instance: SearchRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): SearchRepoInterface {
            return instance ?: SearchRepo(remoteSource)
        }
    }
    override suspend fun getAllProducts(): Response<Products> {
        return remoteSource.getAllProducts()
    }
}