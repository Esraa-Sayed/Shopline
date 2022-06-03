package com.eCommerce.shopify.ui.brandproducts.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.LocalSource
import com.eCommerce.shopify.databinding.FragmentBrandProductsBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.OnProductClickListener
import com.eCommerce.shopify.ui.brandproducts.repo.BrandProductsRepository
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModel
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModelFactory
import com.eCommerce.shopify.ui.favorite.repo.FavoriteRepo
import com.eCommerce.shopify.ui.favorite.viewmodel.FavoriteViewModel
import com.eCommerce.shopify.ui.favorite.viewmodel.FavoriteViewModelFactory

class BrandProductsFragment : Fragment() ,OnProductClickListener{

    private lateinit var binding:FragmentBrandProductsBinding
    private lateinit var myView: View
    private lateinit var navController: NavController

    private lateinit var brandProductsAdapter: BrandProductsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var brandProductsViewModel: BrandProductsViewModel
    private lateinit var brandProductsViewModelFactory: BrandProductsViewModelFactory

    private val args by navArgs<BrandProductsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBrandProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        this.navController = findNavController()
        setupToolbar()
        handleToolbarEvent()
        setupViewModel()
        setupBrandProductsRecycler()


        brandProductsViewModel.getAllFavorites().observe(viewLifecycleOwner) {favItems ->
            brandProductsViewModel.getBrandProductsCollectionList(args.brandTitle)
            brandProductsViewModel.brandProductsCollectionResponse.observe(viewLifecycleOwner) {
                for(favItem in favItems) {
                    for(item in it.products) {
                        if(favItem.id == item.id){
                            Log.i("TAG", "fav truuuuuuuuuuuuuuuuuuue")
                            item.isFavorite = true
                        }
                    }
                }
                brandProductsAdapter.setBrandProductsList(it.products)
                brandProductsAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.appBarHome.toolbar)
        binding.appBarHome.toolbar.title = getString(R.string.products)
    }

    private fun handleToolbarEvent() {
        binding.appBarHome.cardViewFavorite.setOnClickListener {
            navController.navigate(R.id.action_brandProductsFragment_to_favoriteFragment)
        }

        binding.appBarHome.cardViewShoppingCart.setOnClickListener {
            navController.navigate(R.id.action_brandProductsFragment_to_shoppingCartFragment)
        }
    }

    fun setupViewModel(){
        brandProductsViewModelFactory = BrandProductsViewModelFactory(
            BrandProductsRepository.getInstance(APIClient.getInstance(),LocalSource.getInstance(myView.context)),
            FavoriteRepo.getInstance(LocalSource.getInstance(myView.context))
        )
        brandProductsViewModel = ViewModelProvider(this,brandProductsViewModelFactory).get(BrandProductsViewModel::class.java)
    }

    fun setupBrandProductsRecycler(){
        brandProductsAdapter = BrandProductsAdapter(myView.context, emptyList(),this)
        gridLayoutManager = GridLayoutManager(myView.context,2)
        binding.brandProductsRecycler.adapter = brandProductsAdapter
        binding.brandProductsRecycler.layoutManager = gridLayoutManager
    }

    override fun onProductItemClick() {

    }

    override fun onFavBtnClick(product:Product) {
        if(product.isFavorite){
            product.isFavorite=false
            brandProductsViewModel.deleteFromFavorite(product)
        }
        else{
            product.isFavorite=true
            brandProductsViewModel.insertToFavorite(product)
        }
    }
}