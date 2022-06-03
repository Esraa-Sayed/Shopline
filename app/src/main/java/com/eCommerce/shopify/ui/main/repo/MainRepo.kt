package com.eCommerce.shopify.ui.main.repo

import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.model.ProductDetail

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
}