package com.eCommerce.shopify.ui.favorite.repo

import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.LocalSourceInterface
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.Products

class FavoriteRepo private constructor(private val localSource: LocalSourceInterface):FavoriteRepoInterface {

    companion object{
        private val instance: FavoriteRepoInterface? = null
        fun getInstance(localSource: LocalSourceInterface): FavoriteRepoInterface {
            return instance?: FavoriteRepo(localSource)
        }
    }

    override fun getAllFavorites(): LiveData<List<Product>> {
        return localSource.getAllFavorites()
    }

    override fun insertToFavorite(product: Product) {
        localSource.insertToFavorite(product)
    }

    override fun deleteFromFavorite(product: Product) {
        localSource.deleteFromFavorite(product)
    }

}