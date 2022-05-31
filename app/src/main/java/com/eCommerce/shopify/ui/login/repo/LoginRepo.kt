package com.eCommerce.shopify.ui.login.repo

import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.ui.home.repo.HomeRepoInterface
import retrofit2.Response

class LoginRepo private constructor(
    private var remoteSource: RemoteSource
) : LoginRepoInterface {

    companion object {
        private var instance: LoginRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource): LoginRepoInterface {
            return instance ?: LoginRepo(remoteSource)
        }
    }

    override suspend fun getUserDataWithEmail(email:String): Response<UserData> {
        return remoteSource.getUserWithEmail(email)
    }
}