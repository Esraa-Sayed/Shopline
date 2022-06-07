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
import com.eCommerce.shopify.database.favorite.LocalSource
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
import com.eCommerce.shopify.ui.product.view.ProductFragmentDirections
import com.eCommerce.shopify.utils.AppConstants

class BrandProductsFragment : Fragment() ,OnProductClickListener{

    private lateinit var binding:FragmentBrandProductsBinding
    private lateinit var brandProductsAdapter: BrandProductsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var brandProductsViewModel: BrandProductsViewModel
    private lateinit var brandProductsViewModelFactory: BrandProductsViewModelFactory
    private lateinit var navController: NavController

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

    private lateinit var allProduct: List<Product>

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

        Log.d("HelloFromAhmed:", "Brand Id => ${args.brandTitle}")

        this.navController = findNavController()
        setupToolbar()
        handleToolbarEvent()

        binding.appBarHome.toolbar.title = getString(R.string.products)

        brandProductsViewModelFactory = BrandProductsViewModelFactory(
            BrandProductsRepository.getInstance(APIClient.getInstance())
        )
        brandProductsViewModel = ViewModelProvider(this,brandProductsViewModelFactory).get(BrandProductsViewModel::class.java)

        favoriteViewModelFactory = FavoriteViewModelFactory(FavoriteRepo.getInstance(LocalSource.getInstance(requireContext())))
        favoriteViewModel = ViewModelProvider(this,favoriteViewModelFactory).get(FavoriteViewModel::class.java)

        setupBrandProductsRecycler()

        brandProductsViewModel.getBrandProductsCollectionList(args.brandTitle)
        brandProductsViewModel.brandProductsCollectionResponse.observe(viewLifecycleOwner){
            brandProductsAdapter.setBrandProductsList(it.products)
            allProduct = it.products
            brandProductsAdapter.notifyDataSetChanged()
        }
        listenToSearch()

        /*val productsList = listOf(
            Product("shirt",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("shoes",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg"),
            Product("bag",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("dress",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg"),
            Product("bag",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("dress",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg")
        )*/
    }

    private fun listenToSearch(){
        binding.appBarHome.txtInputEditTextSearch.setOnClickListener{

            val action = BrandProductsFragmentDirections.actionBrandProductsFragmentToSearchFragment(
                allProduct = allProduct.toTypedArray(), searchType = AppConstants.PRODUCT
            )
            navController.navigate(action)
        }
    }
    fun setupBrandProductsRecycler(){
        brandProductsAdapter = BrandProductsAdapter(requireContext(), emptyList(),this)
        gridLayoutManager = GridLayoutManager(requireContext(),2)
        binding.brandProductsRecycler.adapter = brandProductsAdapter
        binding.brandProductsRecycler.layoutManager = gridLayoutManager
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.appBarHome.toolbar)
    }

    private fun handleToolbarEvent() {
        binding.appBarHome.cardViewFavorite.setOnClickListener {
            navController.navigate(R.id.action_brandProductsFragment_to_favoriteFragment)
        }

        binding.appBarHome.cardViewShoppingCart.setOnClickListener {
            navController.navigate(R.id.action_brandProductsFragment_to_shoppingCartFragment)
        }
    }

    override fun onProductItemClick() {

    }

    override fun onFavBtnClick(product:Product) {
        if(product.isFavorite){
            product.isFavorite=false
            favoriteViewModel.deleteFromFavorite(product)
        }
        else{
            product.isFavorite=true
            favoriteViewModel.insertToFavorite(product)
        }
    }
}