package com.eCommerce.shopify.ui.login.repo
import com.eCommerce.shopify.model.UserData
import retrofit2.Response

interface LoginRepoInterface {
    suspend fun getUserDataWithEmail(email:String): Response<UserData>
}