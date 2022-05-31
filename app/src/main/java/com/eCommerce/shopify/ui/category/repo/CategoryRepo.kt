package com.eCommerce.shopify.ui.category.repo

import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.network.RemoteSource
import retrofit2.Response

class CategoryRepo private constructor(
    private var remoteSource: RemoteSource
) : CategoryRepoInterface {

    companion object {
        private var instance: CategoryRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): CategoryRepoInterface {
            return instance ?: CategoryRepo(remoteSource)
        }
    }

    override suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory> {
        return remoteSource.getCustomCollectionsCategory()
    }
}