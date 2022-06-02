package com.eCommerce.shopify.ui.product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.ui.product.repo.ProductRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductViewModel(private val _repo: ProductRepoInterface) : ViewModel() {

    private var _categoryProductsResponse = MutableLiveData<Products>()
    val categoryProductsResponse: LiveData<Products> = _categoryProductsResponse

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

    fun getCategoryProducts(id: Long) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _showProgressBar.postValue(true)
            val categoryProducts = _repo.getCategoryProducts(id)
            if (categoryProducts.isSuccessful) {
                _categoryProductsResponse.postValue(categoryProducts.body())
            } else {
                _errorMsgResponse.postValue(categoryProducts.message())
            }
            _showProgressBar.postValue(false)
        }
    }
}