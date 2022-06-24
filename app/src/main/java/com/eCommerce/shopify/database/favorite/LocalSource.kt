package com.eCommerce.shopify.database.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.ShopifyDatabase
import com.eCommerce.shopify.model.Product

class LocalSource(context: Context): LocalSourceInterface {

    private var favoriteDao: FavoriteDao

    init {
        val db = ShopifyDatabase.getInstance(context.applicationContext)
        favoriteDao = db.favoriteDao()
    }

    companion object{
        private var localSourceInstance: LocalSourceInterface? = null

        fun getInstance(context: Context): LocalSourceInterface {
            return localSourceInstance ?: LocalSource(context)
        }
    }


    override fun getAllFavorites(): LiveData<List<Product>> {
        return favoriteDao.getAllFavorites()
    }

    override fun getFavoriteProduct(id: Long): LiveData<Product> {
        return favoriteDao.getFavoriteProduct(id)
    }

    override fun getFavoriteWithUserId(userId: Long): LiveData<List<Product>> {
        return favoriteDao.getFavoriteWithUserId(userId)
    }

    override fun getOneFavoriteWithUserId(id: Long, userId: Long): LiveData<Product> {
        return favoriteDao.getOneFavoriteWithUserId(id,userId)
    }

    override fun insertToFavorite(product: Product) {
        return favoriteDao.insertToFavorite(product)
    }

    override fun deleteFromFavorite(product: Product) {
        return favoriteDao.deleteFromFavorite(product)
    }

    override fun deleteAllFavorites() {
        favoriteDao.deleteAllFavorites()
    }

}