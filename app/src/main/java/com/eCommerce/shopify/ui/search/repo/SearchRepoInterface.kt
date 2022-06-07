package com.eCommerce.shopify.ui.search.repo

import com.eCommerce.shopify.model.Products
import retrofit2.Response

interface SearchRepoInterface {
    suspend fun getAllProducts(): Response<Products>
}