package com.eCommerce.shopify.ui.favorite.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repo:FavoriteRepoInterface):ViewModel(){

    fun getIsLogin(requireContext: Context): Boolean {
        return repo.getIsLogin(context = requireContext)
    }

    fun getUserId(requireContext: Context):Long{
        return repo.getUserId(context = requireContext)
    }

    fun getAllFavorites():LiveData<List<Product>>{
        return repo.getAllFavorites()
    }

    fun getFavoritesWithUserId(userId: Long): LiveData<List<Product>> {
        return repo.getFavoritesWithUserId(userId)
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