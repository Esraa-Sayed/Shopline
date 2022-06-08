package com.eCommerce.shopify.ui.favorite.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
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

    fun getCustomerCurrency(requireContext: Context): String{
        return repo.getCurrency(context = requireContext)
    }

    fun getFavoritesWithUserId(userId: Long): LiveData<List<Product>> {
        return repo.getFavoritesWithUserId(userId)
    }

    fun insertToFavorite(product: Product){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler){
            repo.insertToFavorite(product)
        }
    }

    fun deleteFromFavorite(product: Product){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler){
            repo.deleteFromFavorite(product)
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
        }
    }
}