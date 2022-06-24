package com.eCommerce.shopify.ui.productdetails.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.ProductDetails
import retrofit2.Response

interface ProductDetailsRepoInterface {
    /*suspend*/ fun getCurrencyWithUserEmail(context: Context): String
    suspend fun getProductDetails(context: Context, id: Long): Response<ProductDetails>
    fun getFavoriteProduct(id: Long,userId: Long): LiveData<Product>
    fun insertToFavorite(product: Product)
    fun deleteFromFavorite(product: Product)

    fun getProductInShoppingCart(id: Long): LiveData<ProductDetail>
    fun insertProductInShoppingCart(productDetail: ProductDetail)
    fun deleteProductFromShoppingCart(productDetail: ProductDetail)

    fun isUserLogin(context: Context): Boolean
    fun getUserId(context: Context): Long
}