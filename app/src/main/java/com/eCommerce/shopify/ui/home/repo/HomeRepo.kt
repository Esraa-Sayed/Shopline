package com.eCommerce.shopify.ui.home.repo

import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.network.RemoteSource
import retrofit2.Response

class HomeRepo private constructor(
    private var remoteSource: RemoteSource
) : HomeRepoInterface {

    companion object {
        private var instance: HomeRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): HomeRepoInterface {
            return instance ?: HomeRepo(remoteSource)
        }
    }

    override suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand> {
        return remoteSource.getSmartCollectionsBrand()
    }
}