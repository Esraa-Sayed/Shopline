package com.eCommerce.shopify.ui.main.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.ProductDetail

interface MainRepoInterface {
    val allProductInShoppingCart: LiveData<List<ProductDetail>>
    fun isUserLogin(context: Context): Boolean
}