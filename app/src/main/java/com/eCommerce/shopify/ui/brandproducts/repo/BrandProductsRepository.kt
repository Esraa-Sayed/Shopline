package com.eCommerce.shopify.ui.brandproducts.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.favorite.LocalSourceInterface
import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class BrandProductsRepository private constructor(
    private var remoteSource: RemoteSource,
    private var localSource: LocalSourceInterface
):BrandProductsRepositoryInterface{

    companion object{
        private var instance:BrandProductsRepositoryInterface? = null
        fun getInstance(remoteSource: RemoteSource,localSource: LocalSourceInterface):BrandProductsRepositoryInterface{
            return instance?:BrandProductsRepository(remoteSource,localSource)
        }
    }

    override suspend fun getCollectionWithId(vendor:String): Response<BrandProductsResponse> {
        return remoteSource.getCollectionWithId(vendor)
    }

    override fun getIsLogin(context: Context): Boolean {
        return AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getBooleanValue(AppConstants.IS_LOGIN, false)
    }

    override fun getUserId(context: Context): Long {
        return AppSharedPref.getInstance(context,AppConstants.PREFRENCE_File).getLongValue(AppConstants.USER_ID,0)
    }

    override fun getCurrency(context: Context): String {
        return AppSharedPref.getInstance(context,AppConstants.PREFRENCE_File).getStringValue(AppConstants.CURRENCY,AppConstants.EGP)
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