package com.eCommerce.shopify.ui.product.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.databinding.FragmentProductBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.product.repo.ProductRepo
import com.eCommerce.shopify.ui.product.viewmodel.ProductViewModel
import com.eCommerce.shopify.ui.product.viewmodel.ProductViewModelFactory
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppConstants.ACCESSORIES
import com.eCommerce.shopify.utils.AppConstants.SHOES
import com.eCommerce.shopify.utils.AppConstants.T_SHIRTS

class ProductFragment : Fragment(), OnCategoryProductClickListener {

    private lateinit var productViewModelFactory: ProductViewModelFactory
    private lateinit var viewModel: ProductViewModel
    private var _binding: FragmentProductBinding? = null

    private lateinit var productAdapter: ProductAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var myView: View
    private var allProductList = mutableListOf<Product>()
    private var accessoriesList = mutableListOf<Product>()
    private var tShirtsList = mutableListOf<Product>()
    private var shoesList = mutableListOf<Product>()

    private val mNavController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    private lateinit var productDetailList: List<ProductDetail>

    private val args by navArgs<ProductFragmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        setupToolbar()
        gettingViewModelReady()
        handleUIEvents()
        handleToolbarEvent()
        initRecyclerView()
    }

    private fun setupToolbar() {
        val words = args.categoryName.lowercase().split(" ")
        var productTitleName = ""
        words.forEach { word ->
            productTitleName += word.replaceFirstChar { it.uppercase() } + " "
        }
        binding.appBarHome.toolbar.title = productTitleName.trim().plus(" ").plus(getString(R.string.products))
    }

    private fun gettingViewModelReady() {
        productViewModelFactory = ProductViewModelFactory(
            ProductRepo.getInstance(
                APIClient.getInstance(), ShoppingCartLocalSource(myView.context)
            )
        )
        viewModel = ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]

        viewModel.errorMsgResponse.observe(viewLifecycleOwner, {
            AppConstants.showAlert(
                myView.context,
                R.string.error,
                it,
                R.drawable.ic_error
            )
        })
        viewModel.showProgressBar.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.categoryProductsResponse.observe(viewLifecycleOwner) {
            if (allProductList.isEmpty()) {
                allProductList.addAll(it.products)
                for (product in it.products) {
                    when (product.productType) {
                        SHOES -> {
                            shoesList.add(product)
                        }
                        ACCESSORIES -> {
                            accessoriesList.add(product)
                        }
                        T_SHIRTS -> {
                            tShirtsList.add(product)
                        }
                    }
                }
            }
            renderDataOnScreen(allProductList)
        }

        viewModel.getAllProductInShoppingCartList().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                productDetailList = it
                binding.appBarHome.txtViewCartCount.text = it.size.toString()
                binding.appBarHome.cardViewShoppingCartCount.visibility = View.VISIBLE
            } else {
                binding.appBarHome.cardViewShoppingCartCount.visibility = View.GONE
            }
        }
    }

    private fun renderDataOnScreen(it: MutableList<Product>) {
        productAdapter.setDataToAdapter(it)
    }

    private fun initRecyclerView() {
        productAdapter = ProductAdapter(myView.context, emptyList(), this)
        gridLayoutManager = GridLayoutManager(myView.context, 2, RecyclerView.VERTICAL, false)
        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = gridLayoutManager
        }

        if (allProductList.isEmpty()) {
            viewModel.getCategoryProducts(args.categoryId)
        }
    }

    private fun handleUIEvents() {
        binding.subCategoryRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioBtnAll -> {
                    renderDataOnScreen(allProductList)
                }
                R.id.radioBtnAccessories -> {
                    renderDataOnScreen(accessoriesList)
                }
                R.id.radioBtnTShirts -> {
                    renderDataOnScreen(tShirtsList)
                }
                R.id.radioBtnShoes -> {
                    renderDataOnScreen(shoesList)
                }
            }
        }
    }

    private fun handleToolbarEvent() {
        binding.appBarHome.cardViewFavorite.setOnClickListener {
            val action = ProductFragmentDirections.actionProductFragmentToFavoriteFragment()
            mNavController.navigate(action)
        }

        binding.appBarHome.cardViewShoppingCart.setOnClickListener {
            // send productDetailsList to Shopping Cart Fragment
            val action = ProductFragmentDirections.actionProductFragmentToShoppingCartFragment()
            mNavController.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCategoryProductClick(product: Product) {
        val action = ProductFragmentDirections.actionProductFragmentToProductDetailsFragment(product.id)
        mNavController.navigate(action)
    }
}