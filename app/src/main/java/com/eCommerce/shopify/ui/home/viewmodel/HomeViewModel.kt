package com.eCommerce.shopify.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.ui.home.repo.HomeRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val _repo: HomeRepoInterface) : ViewModel() {

    private var _smartCollectionsBrand = MutableLiveData<SmartCollectionsBrand>()
    val smartCollectionsBrand: LiveData<SmartCollectionsBrand> = _smartCollectionsBrand

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

    private fun getSmartCollectionsBrand() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _showProgressBar.postValue(true)
            val collectionBrands = _repo.getSmartCollectionsBrand()
            if (collectionBrands.isSuccessful) {
                _smartCollectionsBrand.postValue(collectionBrands.body())
            } else {
                _errorMsgResponse.postValue(collectionBrands.message())
            }
        }
    }
}