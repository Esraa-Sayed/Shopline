package com.eCommerce.shopify.ui.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.favorite.LocalSource
import com.eCommerce.shopify.databinding.FragmentFavoriteBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.ui.OnProductClickListener
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepo
import com.eCommerce.shopify.ui.favorite.viewmodel.FavoriteViewModel
import com.eCommerce.shopify.ui.favorite.viewmodel.FavoriteViewModelFactory

class FavoriteFragment : Fragment() ,OnProductClickListener{

    private lateinit var binding:FragmentFavoriteBinding
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
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBarHome.cardViewFavorite.visibility = View.GONE
        binding.appBarHome.cardViewShoppingCart.visibility = View.GONE
        binding.appBarHome.cardViewShoppingCartCount.visibility = View.GONE
        binding.appBarHome.toolbar.title = getString(R.string.wishlist)

        favoriteViewModelFactory = FavoriteViewModelFactory(FavoriteRepo.getInstance(LocalSource.getInstance(requireContext())))
        favoriteViewModel = ViewModelProvider(this,favoriteViewModelFactory).get(FavoriteViewModel::class.java)

        setupFavRecycler()

        favoriteViewModel.getAllFavorites().observe(viewLifecycleOwner){
            favAdapter.setFavProductList(it)
            favAdapter.notifyDataSetChanged()
        }

    }

    fun setupFavRecycler(){
        favAdapter = FavoriteAdapter(requireContext(), emptyList(),this)
        gridManager = GridLayoutManager(requireContext(),2)
        binding.favRecycler.adapter = favAdapter
        binding.favRecycler.layoutManager = gridManager
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.appBarHome.toolbar)
    }

    override fun onProductItemClick() {

    }

    override fun onFavBtnClick(product: Product) {
        product.isFavorite = false
        favoriteViewModel.deleteFromFavorite(product)
    }
}