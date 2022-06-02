package com.eCommerce.shopify.ui.profile.view

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.LocalSource
import com.eCommerce.shopify.databinding.ProfileFragmentBinding
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.profile.repo.ProfileRepo
import com.eCommerce.shopify.ui.profile.view_model.ProfileViewModel
import com.eCommerce.shopify.ui.profile.view_model.ProfileViewModelFactory
import com.eCommerce.shopify.utils.AppConstants

class ProfileFragment : Fragment(), OnOrderListner, OnProductListner {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private lateinit var _binding: ProfileFragmentBinding
    private val binding get() = _binding
    private lateinit var viewModel: ProfileViewModel
    lateinit var ordersAdapter: OrdersAdapter
    lateinit var wishlistAdapter: WishlistAdapter

    private val mNavController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val remoteSource = ProfileRepo.getInstance(APIClient.getInstance(), LocalSource.getInstance(requireContext()))
        val profileFactory = ProfileViewModelFactory(remoteSource)

        viewModel = ViewModelProvider(this, profileFactory)
            .get(ProfileViewModel::class.java)

        checkIfUserLogin()
        initWishlistRecyclerView()
        initOrdersRecyclerView()
        listenToAllBtn()
    }

    private fun getUserOrders() {
        viewModel.getUserOrders(requireContext())
        viewModel.UserOrders.observe(viewLifecycleOwner, {
            if (it.orders.isNotEmpty()){
                ordersAdapter.setOrders(it.orders)
                if(it.orders.size > 2){
                    binding.pMoreOrdersBtn.visibility = View.VISIBLE
                }
                binding.profileLoginConstraintlayout.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
            Log.e("TAG", "getUserOrders: ${it.orders.size}" )
        })
        viewModel.errorMsgResponse.observe(viewLifecycleOwner, {
            AppConstants.showAlert(
                requireContext(),
                R.string.error,
                it,
                R.drawable.ic_error
            )
        })
    }

    private fun getUserWishlist(){
        viewModel.getAllFavorites().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()){
                wishlistAdapter.setWishlist(it)
                if(it.size > 4){
                    binding.pMoreWishlistBtn.visibility = View.VISIBLE
                }
            }
            else{

            }
            binding.profileLoginConstraintlayout.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            Log.e("TAG", "getUserWishlist: ${it.size}" )
        })
    }

    private fun listenToAllBtn(){
        listenToMoreOrdersBtn()
        listenToMoreWishlistBtn()
        listenToLoginBtn()
        listenToRegisterBtn()
    }

    private fun initOrdersRecyclerView(){
        ordersAdapter = OrdersAdapter(this, this)
        binding?.pOrdersRecyclerView?.layoutManager = LinearLayoutManager(this.requireContext()).apply {
            orientation =  LinearLayoutManager.VERTICAL
        }

        binding?.pOrdersRecyclerView?.adapter = ordersAdapter
    }
    private fun initWishlistRecyclerView(){
        wishlistAdapter = WishlistAdapter(this)
        binding?.pWishlistRecyclerView?.layoutManager = GridLayoutManager(this.requireContext(), 2)

        binding?.pWishlistRecyclerView?.adapter = wishlistAdapter
    }

    override fun onOrderClicked(order: Order) {
        //mNavController.navigate(R.id.action_mainFragment_to_ordersDetailsFragment)
    }

    override fun onProductClicked(product: Product) {
//        val action = ProfileFragmentDirections.actionNavigationProfileToOrdersDetailsFragment()
//        mNavController.navigate(action)
    }

    override fun onFavBtnClick(product: Product) {
        product.isFavorite = false
        viewModel.deleteFromFavorite(product)
    }

    private fun listenToMoreWishlistBtn(){
        binding.pMoreWishlistBtn.setOnClickListener{
            onMoreWishlistClicked()
        }
    }
    private fun listenToMoreOrdersBtn(){
        binding.pMoreOrdersBtn.setOnClickListener{
            onMoreOrdersClicked()
        }
    }
    private fun onMoreWishlistClicked(){
        //val action = ProfileFragmentBinding.
    }
    private fun onMoreOrdersClicked(){
        //val action = ProfileFragmentDest
    }
    @SuppressLint("SetTextI18n")
    private fun checkIfUserLogin() {
        if(viewModel.getIsLogin(requireContext())){
            binding.profileNologinRelativelayout.visibility = View.GONE
            binding.profileLoginConstraintlayout.visibility = View.VISIBLE
            binding.pWelcomeNameText.text = "Welcome " + viewModel.getUserName(requireContext())
            getUserOrders()
            getUserWishlist()
        }
        else{
            binding.profileNologinRelativelayout.visibility = View.VISIBLE
            binding.profileLoginConstraintlayout.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun listenToLoginBtn(){
        binding.loginBtn.setOnClickListener {
            mNavController.navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }
    private fun listenToRegisterBtn(){
        binding.registerBtn.setOnClickListener {
            mNavController.navigate(R.id.action_mainFragment_to_registerFragment2)
        }
    }

}