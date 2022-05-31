package com.eCommerce.shopify.ui.product.repo

import com.eCommerce.shopify.model.Products
import retrofit2.Response

interface ProductRepoInterface {
    suspend fun getCategoryProducts(id: Long): Response<Products>
}