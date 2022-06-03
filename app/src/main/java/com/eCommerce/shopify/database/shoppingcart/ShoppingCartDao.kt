package com.eCommerce.shopify.database.shoppingcart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.eCommerce.shopify.model.ProductDetail

@Dao
interface ShoppingCartDao {

    @Query("SELECT * FROM shopping_cart")
    fun getAllProductInShoppingCart(): LiveData<List<ProductDetail>>

    @Query("select * from shopping_cart where id = :id")
    fun getProductInShoppingCart(id: Long): LiveData<ProductDetail>

    @Insert(onConflict = REPLACE)
    fun insertProductInShoppingCart(productDetail: ProductDetail)

    @Delete
    fun deleteProductFromShoppingCart(productDetail: ProductDetail)
}