package com.eCommerce.shopify.ui.favorite.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.favorite.LocalSourceInterface
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref

class FavoriteRepo private constructor(private val localSource: LocalSourceInterface):FavoriteRepoInterface {

    companion object{
        private val instance: FavoriteRepoInterface? = null
        fun getInstance(localSource: LocalSourceInterface): FavoriteRepoInterface {
            return instance?: FavoriteRepo(localSource)
        }
    }

    override fun getIsLogin(context: Context): Boolean{
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getBooleanValue(
            AppConstants.IS_LOGIN, false)
    }

    override fun getUserId(context: Context): Long {
        return AppSharedPref.getInstance(context,AppConstants.PREFRENCE_File).getLongValue(AppConstants.USER_ID,0)
    }

    override fun getAllFavorites(): LiveData<List<Product>> {
        return localSource.getAllFavorites()
    }

    override fun getFavoritesWithUserId(userId: Long): LiveData<List<Product>> {
        return localSource.getFavoriteWithUserId(userId)
    }

    override fun insertToFavorite(product: Product) {
        localSource.insertToFavorite(product)
    }

    override fun deleteFromFavorite(product: Product) {
        localSource.deleteFromFavorite(product)
    }

}