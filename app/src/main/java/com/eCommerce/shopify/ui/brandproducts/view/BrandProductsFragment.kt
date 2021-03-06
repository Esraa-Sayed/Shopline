package com.eCommerce.shopify.ui.brandproducts.view

import android.os.Bundle
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
import com.eCommerce.shopify.ui.brandproducts.repo.BrandProductsRepository
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModel
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModelFactory
import com.eCommerce.shopify.utils.AppConstants
import java.text.NumberFormat
import java.util.*

class BrandProductsFragment : Fragment() , OnProductClickListener {

    private lateinit var binding:FragmentBrandProductsBinding
    private lateinit var myView: View
    private lateinit var navController: NavController

    private lateinit var brandProductsAdapter: BrandProductsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var brandProductsViewModel: BrandProductsViewModel
    private lateinit var brandProductsViewModelFactory: BrandProductsViewModelFactory

    private val args by navArgs<BrandProductsFragmentArgs>()

    private var productsList:List<Product> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        val isLogedin = brandProductsViewModel.getIsLogin(myView.context)
        if (isLogedin) {
            val user_id = brandProductsViewModel.getUserId(myView.context)
            brandProductsViewModel.getBrandProductsCollectionListWithFav(
                args.brandTitle,
                user_id,
                viewLifecycleOwner
            )
            brandProductsViewModel.brandProductsCollectionResponse.observe(viewLifecycleOwner) {
                productsList = it.products
                brandProductsAdapter.setBrandProductsList(productsList)
                brandProductsAdapter.notifyDataSetChanged()
            }
        } else {
            brandProductsViewModel.getBrandProductsCollectionList(args.brandTitle)
            brandProductsViewModel.brandProductsCollectionResponse2.observe(viewLifecycleOwner) {
                productsList = it.products
                brandProductsAdapter.setBrandProductsList(productsList)
                brandProductsAdapter.notifyDataSetChanged()
            }

        }
        brandProductsViewModel.errorMsgResponse.observe(viewLifecycleOwner) {
            AppConstants.showAlert(
                myView.context,
                R.string.error,
                "sorry, there is a problem while showing the products!",
                R.drawable.ic_error
            )
        }
        listenToSearch()

        binding.brandProductsRangedSeekbar.addOnChangeListener { slider, value, fromUser ->
            brandProductsAdapter.setBrandProductsList(productsList.filter {
                val values = binding.brandProductsRangedSeekbar.values
                val price: Float = it.variants[0].price.toFloat()
                price >= values[0] && price <= values[1]
            })
            brandProductsAdapter.notifyDataSetChanged()
        }

        val currentCurrency = brandProductsViewModel.getCustomerCurrency(myView.context)
        if(currentCurrency == "$"){
            binding.minValue.text = "0$"
            val maxInDollar = 1000.0/18.0
            binding.maxValue.text = "${String.format("%.2f", maxInDollar)}$"
        }


        binding.brandProductsRangedSeekbar.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            if(currentCurrency == "$") {
                format.currency = Currency.getInstance("USD")
            }
            else{
                format.currency = Currency.getInstance("EGP")
            }
            format.format(value.toDouble())
        }

    }
    private fun listenToSearch(){
        binding.appBarHome.txtInputEditTextSearch.setOnClickListener{
            val action = BrandProductsFragmentDirections.actionBrandProductsFragmentToSearchFragment(
                allProduct = productsList.toTypedArray(), searchType = AppConstants.PRODUCT
            )
            navController.navigate(action)
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.appBarHome.toolbar)
        binding.appBarHome.toolbar.title = getString(R.string.products)
        binding.appBarHome.cardViewShoppingCartCount.visibility = View.GONE
        binding.appBarHome.cardViewShoppingCart.visibility = View.GONE
    }

    private fun handleToolbarEvent() {
        binding.appBarHome.cardViewFavorite.setOnClickListener {
            navController.navigate(R.id.action_brandProductsFragment_to_favoriteFragment)
        }
    }

    fun setupViewModel(){
        brandProductsViewModelFactory = BrandProductsViewModelFactory(
            BrandProductsRepository.getInstance(APIClient.getInstance(),LocalSource.getInstance(myView.context))
        )
        brandProductsViewModel = ViewModelProvider(this,brandProductsViewModelFactory).get(BrandProductsViewModel::class.java)
    }

    fun setupBrandProductsRecycler(){
        brandProductsAdapter = BrandProductsAdapter(myView.context, emptyList(),this)
        gridLayoutManager = GridLayoutManager(myView.context,2)
        binding.brandProductsRecycler.adapter = brandProductsAdapter
        binding.brandProductsRecycler.layoutManager = gridLayoutManager
    }

    override fun onProductItemClick(productId: Long) {
        val action = BrandProductsFragmentDirections.actionBrandProductsFragmentToProductDetailsFragment(productId)
        navController.navigate(action)
    }

    override fun onFavBtnClick(product:Product) {
        val isLogedin = brandProductsViewModel.getIsLogin(myView.context)
        if(isLogedin) {
            if (product.isFavorite) {
                product.isFavorite = false
                brandProductsViewModel.deleteFromFavorite(product)
            } else {
                val user_id = brandProductsViewModel.getUserId(myView.context)
                product.userId = user_id
                product.isFavorite = true
                brandProductsViewModel.insertToFavorite(product)
            }
        }
        else{
            AppConstants.showDialog(
                requireActivity(),
                getString(R.string.warning),
                getString(R.string.loginWarning),
                getString(R.string.login),
                ::navigateToLogin
            )
        }
    }

    private fun navigateToLogin(){
        navController.navigate(R.id.action_brandProductsFragment_to_loginFragment)
    }

    override fun currencyHandling(): String {
        return brandProductsViewModel.getCustomerCurrency(myView.context)
    }
}