package com.eCommerce.shopify.ui.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.database.LocalSource
import com.eCommerce.shopify.databinding.FragmentFavoriteBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.OnProductClickListener
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepo
import com.eCommerce.shopify.ui.favorite.view.FavoriteAdapter
import com.eCommerce.shopify.ui.favorite.viewmodel.FavoriteViewModel
import com.eCommerce.shopify.ui.favorite.viewmodel.FavoriteViewModelFactory
import com.eCommerce.shopify.ui.register.repo.RegisterRepo
import com.eCommerce.shopify.ui.register.viewmodel.RegisterViewModel
import com.eCommerce.shopify.ui.register.viewmodel.RegisterViewModelFactory

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

    override fun onProductItemClick() {

    }

    override fun onFavBtnClick(product: Product) {
        product.isFavorite = false
        favoriteViewModel.deleteFromFavorite(product)
    }
}