package com.eCommerce.shopify.ui.shopping_cart.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref

interface ShoppingCartRepoInterface {
    fun getIsLogin(context: Context): Boolean
    fun setIsLogin(context: Context, isLogin: Boolean)
    fun insertProductInShoppingCart(productDetail: ProductDetail)
    fun deleteProductFromShopingCart(productDetail: ProductDetail)
    fun getCurrencyFromSharedPref(context: Context): String
    val allProductInShoppingCart: LiveData<List<ProductDetail>>
}