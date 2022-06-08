package com.eCommerce.shopify.ui.search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.CustomCollection
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.SmartCollection
import com.eCommerce.shopify.ui.search.repo.SearchRepoInterface
import com.eCommerce.shopify.utils.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private var allProduct: List<Product>, private val searchType: String, private val repo: SearchRepoInterface) : ViewModel() {

    private var _changedProduct: MutableLiveData<List<Product>> = MutableLiveData()
    var resultProduct: LiveData<List<Product>> = _changedProduct

    private var _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private var _noResult = MutableLiveData<Boolean>()
    val noResult: LiveData<Boolean> = _noResult

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
            _showProgressBar.postValue(false)
        }
    }

    init {
        if(searchType.equals(AppConstants.BRAND) || searchType.equals(AppConstants.CATEGORY)){
            fetchAllProducts()
        }
        else{
            _showProgressBar.postValue(false)
            search("")
        }
    }
    fun search(searchName: String){
        if(!searchName.isEmpty()){
            searchForProduct(searchName)
        }
        else{
            _changedProduct.postValue(allProduct)
        }
    }

    private fun fetchAllProducts() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _showProgressBar.postValue(true)
            val productResponse = repo.getAllProducts()
            if (productResponse.isSuccessful) {
                _changedProduct.postValue(productResponse.body()?.products)
                allProduct = productResponse.body()?.products?: listOf()
            } else {
                _errorMsgResponse.postValue(productResponse.message())
            }
            _showProgressBar.postValue(false)
        }
    }

    private fun searchForProduct(searchName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val products: List<Product>
            products = allProduct.filter{item -> item.title.contains(searchName, ignoreCase = true) }
            if(products.isEmpty()){
                _noResult.postValue(true)
            }
            else{
                _noResult.postValue(false)
                _changedProduct.postValue(products)
            }
        }
    }
}