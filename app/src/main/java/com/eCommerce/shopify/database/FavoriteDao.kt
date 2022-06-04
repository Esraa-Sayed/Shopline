package com.eCommerce.shopify.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.Products

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites():LiveData<List<Product>>

    @Insert(onConflict = REPLACE)
    fun insertToFavorite(product: Product)

    @Delete
    fun deleteFromFavorite(product: Product)

    @Query("DELETE from favorites")
    fun deleteAllFavorites()
}