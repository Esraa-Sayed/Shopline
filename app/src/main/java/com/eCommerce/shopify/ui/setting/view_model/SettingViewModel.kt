package com.eCommerce.shopify.ui.setting.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.ui.setting.repo.AddAddressRepoInterface
import com.eCommerce.shopify.ui.setting.repo.SettingRepo
import com.eCommerce.shopify.ui.setting.repo.SettingRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(var repo: SettingRepoInterface) : ViewModel() {
    private var _UserDataResponse = MutableLiveData<UserData>()
    val UserDataResponse: LiveData<UserData> = _UserDataResponse

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

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
    fun getUserData(requireContext: Context) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val user = repo.getUserDataWithEmail(getUserEmail(requireContext))
            if (user.isSuccessful) {
                _UserDataResponse.postValue(user.body())
            } else {
                _errorMsgResponse.postValue(user.message())
            }
        }
    }
    fun updateCurrencyToUser(context: Context, currency: String){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repo.updateCurrency(context, currency)
            //getUserByEmail(context, repo.getUserEmail(context), currency)
        }
    }

    private fun getUserByEmail(context: Context, userEmail: String,  currency: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val customer = repo.getUserDataWithEmail(userEmail).body()!!.customers[0]
            customer.currency = currency
            updateCustomerToApi(context, customer)
        }
    }

    private fun updateCustomerToApi(context: Context,customer: Customer) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repo.updateCustomerToApi(context, customer)
        }
    }

    fun getCurrencyFromSharedPref(context: Context): String{
        return repo.getCurrencyFromSharedPref(context)
    }
    fun setCurrencyToSharedPref(context: Context, currency: String){
        repo.setCurrencyToSharedPref(context, currency)
    }
}