package com.eCommerce.shopify.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentMainBinding
import com.eCommerce.shopify.utils.NetworkConnectionLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private lateinit var myView: View

    private lateinit var navController: NavController

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
        setupToolbar()
        handleToolbarEvent()
        configureBottomNavView()
        listenerOnNetwork()
        listenToSearch()
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

    private fun handleToolbarEvent() {
        binding.appBarHome.cardViewFavorite.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_favoriteFragment)
        }

        binding.appBarHome.cardViewShoppingCart.setOnClickListener {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}