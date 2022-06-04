package com.eCommerce.shopify.database.shoppingcart

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.eCommerce.shopify.model.ProductDetail

@Dao
interface ShoppingCartDao {

    @get:Query("SELECT * FROM shopping_cart")
    val allProductInShoppingCart: LiveData<List<ProductDetail>>

    @Query("select * from shopping_cart where id = :id")
    fun getProductInShoppingCart(id: Long): LiveData<ProductDetail>

    @Insert(onConflict = REPLACE)
    fun insertProductInShoppingCart(productDetail: ProductDetail)

    @Delete
    fun deleteProductFromShoppingCart(productDetail: ProductDetail)
}