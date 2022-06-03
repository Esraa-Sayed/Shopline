package com.eCommerce.shopify.database.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.eCommerce.shopify.model.Product

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getAllFavorites():LiveData<List<Product>>

    @Query("select * from favorites where id = :id")
    fun getFavoriteProduct(id: Long): LiveData<Product>

    @Insert(onConflict = REPLACE)
    fun insertToFavorite(product: Product)

    @Delete
    fun deleteFromFavorite(product: Product)
}