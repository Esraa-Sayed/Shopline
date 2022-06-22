package com.eCommerce.shopify.ui.product.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.Products
import retrofit2.Response

interface ProductRepoInterface {
    suspend fun getCategoryProducts(id: Long): Response<Products>
    val allProductInShoppingCart: LiveData<List<ProductDetail>>
    fun isUserLogin(context: Context): Boolean
}