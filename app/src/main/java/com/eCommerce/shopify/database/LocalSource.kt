package com.eCommerce.shopify.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.Products

class LocalSource(context: Context):LocalSourceInterface {

    private var favoriteDao:FavoriteDao

    init {
        val db = ShopifyDatabase.getInstance(context.applicationContext)
        favoriteDao = db.favoriteDao()
    }

    companion object{
        private var localSourceInstance:LocalSourceInterface? = null

        fun getInstance(context: Context):LocalSourceInterface{
            return localSourceInstance?:LocalSource(context)
        }
    }


    override fun getAllFavorites(): LiveData<List<Product>> {
        return favoriteDao.getAllFavorites()
    }

    override fun insertToFavorite(product: Product) {
        return favoriteDao.insertToFavorite(product)
    }

    override fun deleteFromFavorite(product: Product) {
        return favoriteDao.deleteFromFavorite(product)
    }
}