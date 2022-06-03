package com.eCommerce.shopify.ui.brandproducts.repo

import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.LocalSourceInterface
import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.network.RemoteSource
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

    override fun getAllFavorites(): LiveData<List<Product>> {
        return localSource.getAllFavorites()
    }

}