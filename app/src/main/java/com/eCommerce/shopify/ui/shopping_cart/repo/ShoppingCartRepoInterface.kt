package com.eCommerce.shopify.ui.shopping_cart.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.ProductDetail

interface ShoppingCartRepoInterface {
    fun getIsLogin(context: Context): Boolean
    fun setIsLogin(context: Context, isLogin: Boolean)
    fun updateProductInShoppingCart(productDetail: ProductDetail)
    fun deleteProductFromShoppingCart(productDetail: ProductDetail)
    fun getCurrencyFromSharedPref(context: Context): String
    val allProductInShoppingCart: LiveData<List<ProductDetail>>
}