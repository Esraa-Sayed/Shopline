package com.eCommerce.shopify.ui.productdetails.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.ProductDetails
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.ui.product.repo.ProductRepoInterface
import com.eCommerce.shopify.ui.productdetails.repo.ProductDetailsRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductDetailsViewModel(private val _repo: ProductDetailsRepoInterface) : ViewModel() {

    private var _productDetailsResponse = MutableLiveData<ProductDetails>()
    val productDetailsResponse: LiveData<ProductDetails> = _productDetailsResponse

    private var _currencyResponse = MutableLiveData<String>()
    val currencyResponse: LiveData<String> = _currencyResponse

    private var _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
            _showProgressBar.postValue(false)
        }
    }

    fun getCurrencyWithUserEmail(context: Context) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _currencyResponse.postValue(_repo.getCurrencyWithUserEmail(context))
        }
    }

    fun getCategoryProducts(id: Long) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _showProgressBar.postValue(true)
            val productDetails = _repo.getProductDetails(id)
            if (productDetails.isSuccessful) {
                _productDetailsResponse.postValue(productDetails.body())
            } else {
                _errorMsgResponse.postValue(productDetails.message())
            }
            _showProgressBar.postValue(false)
        }
    }
}