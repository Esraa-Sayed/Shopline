package com.eCommerce.shopify.database.favorite

import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.Product

interface LocalSourceInterface {

    fun getAllFavorites(): LiveData<List<Product>>
    fun getFavoriteProduct(id: Long): LiveData<Product>
    fun getFavoriteWithUserId(userId:Long):LiveData<List<Product>>
    fun getOneFavoriteWithUserId(id: Long,userId:Long):LiveData<Product>
    fun insertToFavorite(product: Product)
    fun deleteFromFavorite(product: Product)
    fun deleteAllFavorites()

}