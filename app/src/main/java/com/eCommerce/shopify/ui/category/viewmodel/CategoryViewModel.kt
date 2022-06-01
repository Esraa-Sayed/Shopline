package com.eCommerce.shopify.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.ui.category.repo.CategoryRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(private val _repo: CategoryRepoInterface) : ViewModel() {

    private var _customCollectionsCategoryResponse = MutableLiveData<CustomCollectionsCategory>()
    val customCollectionsCategoryResponse: LiveData<CustomCollectionsCategory> = _customCollectionsCategoryResponse

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

    fun getCustomCollectionsCategory() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _showProgressBar.postValue(true)
            val collectionBrands = _repo.getCustomCollectionsCategory()
            if (collectionBrands.isSuccessful) {
                _customCollectionsCategoryResponse.postValue(collectionBrands.body())
            } else {
                _errorMsgResponse.postValue(collectionBrands.message())
            }
            _showProgressBar.postValue(false)
        }
    }
}