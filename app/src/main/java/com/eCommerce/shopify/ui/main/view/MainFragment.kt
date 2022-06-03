package com.eCommerce.shopify.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.databinding.FragmentMainBinding
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.ui.main.repo.MainRepo
import com.eCommerce.shopify.ui.main.viewmodel.MainViewModel
import com.eCommerce.shopify.ui.main.viewmodel.MainViewModelFactory
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.NetworkConnectionLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {

    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private lateinit var myView: View

    private lateinit var navController: NavController

    private lateinit var productDetailList: List<ProductDetail>

    //lateinit var navigationView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        this.navController = findNavController()
//        navigationView = binding.navView as NavigationView
//        navigationView.setNavigationItemSelectedListener(this)

        //binding.navView.set
        setupToolbar()
        handleToolbarEvent()
        configureBottomNavView()

        listenerOnNetwork()
        listenToSearch()
        gettingViewModelReady()
        //listenOnBottomNavigation()
    }

    private fun listenerOnNetwork() {
        NetworkConnectionLiveData(myView.context).observe(this, {
            if (it) {
                binding.containerView.visibility = View.VISIBLE
                binding.connectionLostMain.root.visibility = View.GONE
            } else {
                binding.containerView.visibility = View.GONE
                binding.connectionLostMain.root.visibility = View.VISIBLE
            }
        })
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.appBarHome.toolbar)
    }

    private fun listenToSearch(){
        binding.appBarHome.txtInputEditTextSearch.setOnClickListener{
            navController.navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    private fun gettingViewModelReady() {
        mainViewModelFactory = MainViewModelFactory(
            MainRepo.getInstance(
                ShoppingCartLocalSource(myView.context)
            )
        )
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        viewModel.errorMsgResponse.observe(viewLifecycleOwner, {
            AppConstants.showAlert(
                myView.context,
                R.string.error,
                it,
                R.drawable.ic_error
            )
        })

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

    private fun handleToolbarEvent() {
        binding.appBarHome.cardViewFavorite.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_favoriteFragment)
        }

        binding.appBarHome.cardViewShoppingCart.setOnClickListener {
            // send productDetailsList to Shopping Cart Fragment
            navController.navigate(R.id.action_mainFragment_to_shoppingCartFragment)
        }
    }

    private fun configureBottomNavView() {
        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        val navController = (childFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment).navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_categories,
                R.id.navigation_profile,
                R.id.navigation_setting
            )
        )
        (activity as AppCompatActivity).setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.navigation_home -> {
                    binding.appBarHome.textInputLayout.visibility = View.VISIBLE
                    binding.appBarHome.cardViewFavorite.visibility = View.VISIBLE
                }
                R.id.navigation_categories -> {
                    binding.appBarHome.textInputLayout.visibility = View.VISIBLE
                    binding.appBarHome.cardViewFavorite.visibility = View.VISIBLE
                }
                R.id.navigation_profile -> {
                    binding.appBarHome.textInputLayout.visibility = View.GONE
                    binding.appBarHome.cardViewFavorite.visibility = View.GONE
                }
                R.id.navigation_setting -> {
                    binding.appBarHome.textInputLayout.visibility = View.GONE
                    binding.appBarHome.cardViewFavorite.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}