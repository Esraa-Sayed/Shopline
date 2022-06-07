package com.eCommerce.shopify.ui.search.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eCommerce.shopify.model.CustomCollection
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.SmartCollection
import com.eCommerce.shopify.ui.search.repo.SearchRepoInterface
import java.lang.IllegalArgumentException

class SearchViewModelFactory(private val allProduct :List<Product>, private val searchType: String, private val repo: SearchRepoInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel:: class.java)){
            SearchViewModel(allProduct, searchType, repo) as T
        }
        else{
            throw IllegalArgumentException("SearchViewModel class not found")
        }
    }
}