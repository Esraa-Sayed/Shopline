package com.eCommerce.shopify.ui.profile.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.OrderModel
import com.eCommerce.shopify.ui.profile.repo.ProfileRepoInterface
import com.eCommerce.shopify.utils.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(val _repo: ProfileRepoInterface) : ViewModel() {

    private var _UserOrders = MutableLiveData<OrderModel>()
    val UserOrders: LiveData<OrderModel> = _UserOrders

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

    fun getUserOrders(context: Context) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val userId = _repo.getUserIdFromSharedPref(context, AppConstants.USER_ID,0)
            val user = _repo.getUserOrdersWithId(userId)
            if (user.isSuccessful) {
                _UserOrders.postValue(user.body())
            } else {
                _errorMsgResponse.postValue(user.message())
            }
        }
    }

    fun getIsLogin(requireContext: Context): Boolean {
        return _repo.getIsLogin(context = requireContext)
    }

    fun setIsLogin(requireContext: Context, isLogin: Boolean) {
        return _repo.setIsLogin(context = requireContext, isLogin)
    }
}