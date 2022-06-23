package com.eCommerce.shopify.ui.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.favorite.LocalSource
import com.eCommerce.shopify.databinding.FragmentFavoriteBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.brandproducts.view.OnProductClickListener
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepo
import com.eCommerce.shopify.ui.favorite.viewmodel.FavoriteViewModel
import com.eCommerce.shopify.ui.favorite.viewmodel.FavoriteViewModelFactory

class FavoriteFragment : Fragment() , OnProductClickListener {

    private lateinit var binding:FragmentFavoriteBinding
    private lateinit var myView: View
    private lateinit var navController: NavController

    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var gridManager:GridLayoutManager

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        this.navController = findNavController()
        setupToolbar()
        setupViewModel()

        val isLogedin = favoriteViewModel.getIsLogin(myView.context)

        if(isLogedin) {
            showFavorites()
        }
        else{
            handleRegisterAndLoginBtn()
        }

        binding.appBarHome.backArrow.setOnClickListener {
            navController.navigateUp()
        }

    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.appBarHome.toolbar)
        binding.appBarHome.toolbar.title = getString(R.string.wishlist)
    }

    fun setupViewModel(){
        favoriteViewModelFactory = FavoriteViewModelFactory(FavoriteRepo.getInstance(LocalSource.getInstance(myView.context)))
        favoriteViewModel = ViewModelProvider(this,favoriteViewModelFactory).get(FavoriteViewModel::class.java)
    }

    fun setupFavRecycler(){
        favAdapter = FavoriteAdapter(myView.context, emptyList(),this)
        gridManager = GridLayoutManager(myView.context,2)
        binding.favRecycler.adapter = favAdapter
        binding.favRecycler.layoutManager = gridManager
    }

    fun showFavorites(){
        binding.notLoginConstraint.visibility = View.GONE
        setupFavRecycler()
        val user_id = favoriteViewModel.getUserId(myView.context)
        favoriteViewModel.getFavoritesWithUserId(user_id).observe(viewLifecycleOwner){
            if(it.size != 0) binding.linearEmptyFavorite.visibility = View.GONE
            favAdapter.setFavProductList(it)
            favAdapter.notifyDataSetChanged()
        }
    }

    fun handleRegisterAndLoginBtn(){
        binding.loginBtn.setOnClickListener {
            navController.navigate(R.id.action_favoriteFragment_to_loginFragment)
        }
        binding.registerBtn.setOnClickListener {
            navController.navigate(R.id.action_favoriteFragment_to_registerFragment)
        }
    }

    override fun onProductItemClick(productId:Long) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToProductDetailsFragment(productId)
        navController.navigate(action)
    }

    override fun onFavBtnClick(product: Product) {
        product.isFavorite = false
        favoriteViewModel.deleteFromFavorite(product)
        val user_id = favoriteViewModel.getUserId(myView.context)
        favoriteViewModel.getFavoritesWithUserId(user_id).observe(viewLifecycleOwner){
            if(it.size == 0) binding.linearEmptyFavorite.visibility = View.VISIBLE
            favAdapter.setFavProductList(it)
            favAdapter.notifyDataSetChanged()
        }
    }

    override fun currencyHandling(): String {
        return favoriteViewModel.getCustomerCurrency(myView.context)
    }
}