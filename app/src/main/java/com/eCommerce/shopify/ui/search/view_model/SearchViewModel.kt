package com.eCommerce.shopify.ui.search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eCommerce.shopify.model.SmartCollection
import com.eCommerce.shopify.ui.favorite.model.Product

class SearchViewModel(val allBrands: List<SmartCollection>,val allProduct: List<Product>) : ViewModel() {

    private var changedProduct: MutableLiveData<List<Product>> = MutableLiveData()
    var resultProduct: LiveData<List<Product>> = changedProduct

    private var changedSmartCollection: MutableLiveData<List<SmartCollection>> = MutableLiveData()
    var resultSmartCollection: LiveData<List<SmartCollection>> = changedSmartCollection

    fun searchForBrand(searchName: String){
        var smartCollection: List<SmartCollection>
        allBrands.filter{ actor -> searchName.equals(actor) }.also { smartCollection = it }
        changedSmartCollection = smartCollection as MutableLiveData<List<SmartCollection>>
    }

    fun searchForProduct(searchName: String){
        var product: List<Product>
        allProduct.filter{ actor -> searchName.equals(actor) }.also { product = it }
        changedProduct = product as MutableLiveData<List<Product>>
    }
}