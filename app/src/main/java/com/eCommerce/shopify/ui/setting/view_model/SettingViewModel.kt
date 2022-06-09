package com.eCommerce.shopify.ui.setting.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eCommerce.shopify.ui.setting.repo.SettingRepoInterface

class SettingViewModel(var repo: SettingRepoInterface) : ViewModel() {

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    fun getIsLogin(requireContext: Context): Boolean {
        return repo.getIsLogin(context = requireContext)
    }

    fun setIsLogin(requireContext: Context, isLogin: Boolean) {
        return repo.setIsLogin(context = requireContext, isLogin)
    }
    fun getUserName(requireContext: Context): String{
        return repo.getUserName(requireContext)
    }
    fun getUserEmail(requireContext: Context): String{
        return repo.getUserEmail(requireContext)
    }

    fun getCurrencyFromSharedPref(context: Context): String{
        return repo.getCurrencyFromSharedPref(context)
    }
    fun setCurrencyToSharedPref(context: Context, currency: String){
        repo.setCurrencyToSharedPref(context, currency)
    }
}