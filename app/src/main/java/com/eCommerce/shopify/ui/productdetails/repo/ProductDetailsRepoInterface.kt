package com.eCommerce.shopify.ui.productdetails.repo

import android.content.Context
import com.eCommerce.shopify.model.ProductDetails
import retrofit2.Response

interface ProductDetailsRepoInterface {
    suspend fun getCurrencyWithUserEmail(context: Context): String
    suspend fun getProductDetails(id: Long): Response<ProductDetails>
}