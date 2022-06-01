package com.eCommerce.shopify.ui.search.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.SmartCollection
import java.lang.IllegalArgumentException

class SearchViewModelFactory(private val allBrands: List<SmartCollection>, private val allProduct :List<Product>): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel:: class.java)){
            SearchViewModel(allBrands, allProduct) as T
        }
        else{
            throw IllegalArgumentException("SearchViewModel class not found")
        }
    }
}