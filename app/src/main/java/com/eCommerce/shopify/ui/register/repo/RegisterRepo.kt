package com.eCommerce.shopify.ui.register.repo

import android.content.Context
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class RegisterRepo private constructor(private val remoteSource:RemoteSource):
    RegisterRepoInterface {

    companion object{
        private val instance: RegisterRepoInterface? = null
        fun getInstance(remoteSource:RemoteSource): RegisterRepoInterface {
            return instance ?: RegisterRepo(remoteSource)
        }
    }

    override suspend fun registerCustomer(customer: CustomerResponse): Response<CustomerResponse> {
        return remoteSource.registerCustomer(customer)
    }

    override fun saveDataInSharedPref(
        context: Context,
        email: String,
        userId: Long,
        userName: String
    ) {
        val fileSharedPref = AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File)
        fileSharedPref.setValue(AppConstants.IS_LOGIN,true)
        fileSharedPref.setValue(AppConstants.USER_EMAIL,email)
        fileSharedPref.setValue(AppConstants.USER_ID,userId)
        fileSharedPref.setValue(AppConstants.USER_NAME,userName)
    }

}