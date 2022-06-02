package com.eCommerce.shopify.database.shoppingcart

import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.ProductDetail

interface ShoppingCartLocalSourceInterface {

    fun getAllProductInShoppingCart(): LiveData<List<ProductDetail>>
    fun getProductInShoppingCart(id: Long): LiveData<ProductDetail>
    fun insertProductInShoppingCart(productDetail: ProductDetail)
    fun deleteProductFromShoppingCart(productDetail: ProductDetail)
}