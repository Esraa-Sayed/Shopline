package com.eCommerce.shopify.database

import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.Product

interface LocalSourceInterface {

    fun getAllFavorites(): LiveData<List<Product>>
    fun insertToFavorite(product: Product)
    fun deleteFromFavorite(product: Product)
}