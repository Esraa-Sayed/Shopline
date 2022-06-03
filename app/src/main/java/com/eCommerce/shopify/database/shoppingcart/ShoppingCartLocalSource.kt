package com.eCommerce.shopify.database.shoppingcart

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.ShopifyDatabase
import com.eCommerce.shopify.model.ProductDetail

class ShoppingCartLocalSource(context: Context): ShoppingCartLocalSourceInterface {

    private var shoppingCartDao: ShoppingCartDao
    override val allProductInShoppingCart: LiveData<List<ProductDetail>>

    init {
        val db = ShopifyDatabase.getInstance(context.applicationContext)
        shoppingCartDao = db.shoppingCartDao()
        allProductInShoppingCart = shoppingCartDao.allProductInShoppingCart
    }

    companion object{
        private var localSourceInstance: ShoppingCartLocalSourceInterface? = null

        fun getInstance(context: Context): ShoppingCartLocalSourceInterface {
            return localSourceInstance ?: ShoppingCartLocalSource(context)
        }
    }

    override fun getProductInShoppingCart(id: Long): LiveData<ProductDetail> {
        return shoppingCartDao.getProductInShoppingCart(id)
    }

    override fun insertProductInShoppingCart(productDetail: ProductDetail) {
        return shoppingCartDao.insertProductInShoppingCart(productDetail)
    }

    override fun deleteProductFromShoppingCart(productDetail: ProductDetail) {
        return shoppingCartDao.deleteProductFromShoppingCart(productDetail)
    }
}