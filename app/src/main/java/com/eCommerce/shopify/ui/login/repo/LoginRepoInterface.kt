package com.eCommerce.shopify.ui.login.repo
import android.content.Context
import com.eCommerce.shopify.model.UserData
import retrofit2.Response

interface LoginRepoInterface {
    suspend fun getUserDataWithEmail(email:String): Response<UserData>
    fun saveDataInSharedPref(context: Context,email: String, userId: Long, userName:String)
}