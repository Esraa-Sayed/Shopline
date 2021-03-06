package com.eCommerce.shopify.ui.profile.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.OrderModel
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.profile.repo.ProfileRepoInterface
import com.eCommerce.shopify.utils.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(val _repo: ProfileRepoInterface) : ViewModel() {

    private var _userOrders = MutableLiveData<OrderModel>()
    val userOrders: LiveData<OrderModel> = _userOrders

    private var _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean> = _showProgressBar

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
                _userOrders.postValue(user.body())
                _showProgressBar.postValue(true)
            } else {
                _errorMsgResponse.postValue(user.message())
                _showProgressBar.postValue(true)
            }
        }
    }

    fun getIsLogin(requireContext: Context): Boolean {
        return _repo.getIsLogin(context = requireContext)
    }

    fun getAllFavorites(context: Context): LiveData<List<Product>>{
        return _repo.getAllFavorites(context)
    }

    fun insertToFavorite(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            _repo.insertToFavorite(product)
        }
    }

    fun deleteFromFavorite(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            _repo.deleteFromFavorite(product)

        }
    }
    fun getUserName(requireContext: Context): String{
        return _repo.getUserName(requireContext)
    }
    fun getCurrencyFromSharedPref(context: Context): String{
        return _repo.getCurrencyFromSharedPref(context)
    }
}