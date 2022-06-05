package com.eCommerce.shopify.ui.brandproducts.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.navArgs
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.favorite.LocalSource
import com.eCommerce.shopify.databinding.ConfirmDialogBinding
import com.eCommerce.shopify.databinding.FragmentBrandProductsBinding
import com.eCommerce.shopify.databinding.GoToLoginDialogBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.brandproducts.repo.BrandProductsRepository
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModel
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModelFactory
import com.eCommerce.shopify.ui.favorite.view.FavoriteFragmentDirections
import com.eCommerce.shopify.utils.AppConstants

class BrandProductsFragment : Fragment() , OnProductClickListener {

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
        if(isLogedin) {
            val user_id = brandProductsViewModel.getUserId(myView.context)
            brandProductsViewModel.getBrandProductsCollectionListWithFav(
                args.brandTitle,
                user_id,
                viewLifecycleOwner
            )
            brandProductsViewModel.brandProductsCollectionResponse.observe(viewLifecycleOwner) {
                brandProductsAdapter.setBrandProductsList(it.products)
                brandProductsAdapter.notifyDataSetChanged()
            }
        }
        else{
            brandProductsViewModel.getBrandProductsCollectionList(args.brandTitle)
            brandProductsViewModel.brandProductsCollectionResponse2.observe(viewLifecycleOwner){
                brandProductsAdapter.setBrandProductsList(it.products)
                brandProductsAdapter.notifyDataSetChanged()
            }
        }

        brandProductsViewModel.errorMsgResponse.observe(viewLifecycleOwner){
            AppConstants.showAlert(
                myView.context,
                R.string.error,
                "sorry, there is a problem while showing the products!",
                R.drawable.ic_error
            )
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
            val inflater = requireActivity().layoutInflater
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val bind : GoToLoginDialogBinding = GoToLoginDialogBinding.inflate(inflater)
            dialog.setContentView(bind.root)
            dialog.setTitle(getString(R.string.warning))
            bind.okBtn.setOnClickListener {
                dialog.dismiss()
            }
            bind.goToLogin.setOnClickListener{
                navController.navigate(R.id.action_brandProductsFragment_to_loginFragment)
                dialog.dismiss()
            }
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
    }
}