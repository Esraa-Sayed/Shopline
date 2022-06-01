package com.eCommerce.shopify.ui.brandproducts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.ui.brandproducts.repo.BrandProductsRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BrandProductsViewModel(private val repo: BrandProductsRepositoryInterface) : ViewModel() {

    private var _brandProductsCollectionResponse = MutableLiveData<BrandProductsResponse>()
    var brandProductsCollectionResponse:LiveData<BrandProductsResponse> = _brandProductsCollectionResponse

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    fun getBrandProductsCollectionList(vendor:String){
        viewModelScope.launch(Dispatchers.IO) {
            val brandCollectionProducts = repo.getCollectionWithId(vendor)

            if (brandCollectionProducts.isSuccessful) {
                //Log.d("asssssss:", collectionBrands.toString())
                _brandProductsCollectionResponse.postValue(brandCollectionProducts.body())
            } else {
                //Log.d("assssssshh:", collectionBrands.toString())
                _errorMsgResponse.postValue(brandCollectionProducts.message())
            }
        }
    }

}