package com.eCommerce.shopify.ui.login.repo

import android.content.Context
import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.ui.home.repo.HomeRepoInterface
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
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

    override fun saveDataInSharedPref(context: Context,email: String, userId: Long,userName:String, currency: String) {
        val fileSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        fileSharedPref.setValue(AppConstants.IS_LOGIN,true)
        fileSharedPref.setValue(AppConstants.USER_EMAIL,email)
        fileSharedPref.setValue(AppConstants.USER_ID,userId)
        fileSharedPref.setValue(AppConstants.USER_NAME,userName)
        fileSharedPref.setValue(AppConstants.CURRENCY, currency)

    }

}