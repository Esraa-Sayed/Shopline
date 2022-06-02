package com.eCommerce.shopify.ui.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repo:FavoriteRepoInterface):ViewModel(){

    fun getAllFavorites():LiveData<List<Product>>{
        return repo.getAllFavorites()
    }

    fun insertToFavorite(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertToFavorite(product)
        }
    }

    fun deleteFromFavorite(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteFromFavorite(product)
        }
    }

}