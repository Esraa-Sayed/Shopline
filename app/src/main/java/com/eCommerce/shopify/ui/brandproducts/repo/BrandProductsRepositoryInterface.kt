package com.eCommerce.shopify.ui.brandproducts.repo

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.model.Product
import retrofit2.Response

interface BrandProductsRepositoryInterface {
    //remote source
    suspend fun getCollectionWithId(vendor:String): Response<BrandProductsResponse>

    //local source
    fun getAllFavorites(): LiveData<List<Product>>

    fun insertToFavorite(product: Product)

    fun deleteFromFavorite(product: Product)
}