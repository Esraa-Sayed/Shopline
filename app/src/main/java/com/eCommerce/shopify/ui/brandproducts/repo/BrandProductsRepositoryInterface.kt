package com.eCommerce.shopify.ui.brandproducts.repo

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.model.Product
import retrofit2.Response

interface BrandProductsRepositoryInterface {
    //remote source
    suspend fun getCollectionWithId(vendor:String): Response<BrandProductsResponse>

    //local source
    fun getIsLogin(context: Context): Boolean

    fun getUserId(context: Context): Long

    fun getAllFavorites(): LiveData<List<Product>>

    fun getFavoritesWithUserId(userId: Long): LiveData<List<Product>>

    fun insertToFavorite(product: Product)

    fun deleteFromFavorite(product: Product)
}