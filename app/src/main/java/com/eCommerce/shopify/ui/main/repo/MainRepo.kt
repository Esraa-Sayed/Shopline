package com.eCommerce.shopify.ui.main.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref

class MainRepo private constructor(
    private var shoppingCartLocalSource: ShoppingCartLocalSource
) : MainRepoInterface {

    companion object {
        private var instance: MainRepoInterface? = null
        fun getInstance(shoppingCartLocalSource: ShoppingCartLocalSource): MainRepoInterface {
            return instance ?: MainRepo(shoppingCartLocalSource)
        }
    }

    override val allProductInShoppingCart: LiveData<List<ProductDetail>>
        get() = shoppingCartLocalSource.allProductInShoppingCart

    override fun isUserLogin(context: Context): Boolean {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getBooleanValue(
            AppConstants.IS_LOGIN, false)
    }
}