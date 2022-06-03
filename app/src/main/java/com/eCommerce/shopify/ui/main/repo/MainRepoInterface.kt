package com.eCommerce.shopify.ui.main.repo

import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.ProductDetail

interface MainRepoInterface {
    val allProductInShoppingCart: LiveData<List<ProductDetail>>
}