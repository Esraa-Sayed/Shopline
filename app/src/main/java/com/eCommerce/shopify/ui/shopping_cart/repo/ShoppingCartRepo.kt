package com.eCommerce.shopify.ui.shopping_cart.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSourceInterface
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref

class ShoppingCartRepo(val shoppingCartLocalSource: ShoppingCartLocalSourceInterface): ShoppingCartRepoInterface {

    companion object{
        private var instance: ShoppingCartRepoInterface? = null
        fun getInstance(shoppingCartLocalSource: ShoppingCartLocalSourceInterface): ShoppingCartRepoInterface{
            return instance?: ShoppingCartRepo(shoppingCartLocalSource)
        }
    }
    override fun getIsLogin(context: Context): Boolean{
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getBooleanValue(AppConstants.IS_LOGIN, false)
    }

    override fun setIsLogin(context: Context, isLogin: Boolean) {
        AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).setValue(AppConstants.IS_LOGIN, false)
    }

    override fun updateProductInShoppingCart(productDetail: ProductDetail) {
        shoppingCartLocalSource.insertProductInShoppingCart(productDetail)
    }

    override fun deleteProductFromShoppingCart(productDetail: ProductDetail) {
        shoppingCartLocalSource.deleteProductFromShoppingCart(productDetail)
    }
    override fun getCurrencyFromSharedPref(context: Context): String {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getStringValue(AppConstants.CURRENCY, AppConstants.EGP)
    }
    override val allProductInShoppingCart: LiveData<List<ProductDetail>>
        get() = shoppingCartLocalSource.allProductInShoppingCart
}