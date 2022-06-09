package com.eCommerce.shopify.ui.brandproducts.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.eCommerce.shopify.model.BrandProductsResponse
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.brandproducts.repo.BrandProductsRepositoryInterface
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BrandProductsViewModel(private val repo: BrandProductsRepositoryInterface) : ViewModel() {

    private var _brandProductsCollectionResponse = MutableLiveData<BrandProductsResponse>()
    var brandProductsCollectionResponse:LiveData<BrandProductsResponse> = _brandProductsCollectionResponse

    private var _brandProductsCollectionResponse2 = MutableLiveData<BrandProductsResponse>()
    var brandProductsCollectionResponse2:LiveData<BrandProductsResponse> = _brandProductsCollectionResponse2

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

    fun getBrandProductsCollectionList(vendor:String){
        viewModelScope.launch(Dispatchers.IO  + coroutineExceptionHandler) {
            val brandCollectionProducts = repo.getCollectionWithId(vendor)

            if (brandCollectionProducts.isSuccessful) {
                //Log.d("asssssss:", collectionBrands.toString())
                _brandProductsCollectionResponse2.postValue(brandCollectionProducts.body())
            } else {
                //Log.d("assssssshh:", collectionBrands.toString())
                _errorMsgResponse.postValue(brandCollectionProducts.message())
            }
        }
    }

    /*fun getAllFavorites():LiveData<List<Product>>{
        return repo.getAllFavorites()
    }*/

    fun getFavoritesWithUserId(userId: Long): LiveData<List<Product>> {
        return repo.getFavoritesWithUserId(userId)
    }

    fun getBrandProductsCollectionListWithFav(vendor:String,userId:Long,owner:LifecycleOwner){
        getBrandProductsCollectionList(vendor)
        brandProductsCollectionResponse2.observe(owner){
            getFavoritesWithUserId(userId).observe(owner) { favItems ->
                for(favItem in favItems) {
                    for(item in it.products) {
                        if(favItem.id == item.id){
                            Log.i("TAG", "fav truuuuuuuuuuuuuuuuuuue")
                            item.isFavorite = true
                        }
                    }
                }
            }
            _brandProductsCollectionResponse.postValue(it)
        }
    }

    fun getIsLogin(requireContext: Context): Boolean {
        return repo.getIsLogin(context = requireContext)
    }

    fun getUserId(requireContext: Context): Long{
        return repo.getUserId(context = requireContext)
    }

    fun getCustomerCurrency(requireContext: Context): String{
        return repo.getCurrency(context = requireContext)
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