package com.eCommerce.shopify.ui.favorite.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.Product

interface FavoriteRepoInterface {
    fun getIsLogin(context: Context): Boolean
    fun getAllFavorites(): LiveData<List<Product>>
    fun insertToFavorite(product: Product)
    fun deleteFromFavorite(product: Product)
}