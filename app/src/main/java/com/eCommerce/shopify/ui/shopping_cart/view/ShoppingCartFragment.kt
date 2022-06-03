package com.eCommerce.shopify.ui.shopping_cart.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.ProfileFragmentBinding
import com.eCommerce.shopify.databinding.ShoppingCartFragmentBinding
import com.eCommerce.shopify.ui.shopping_cart.repo.ShoppingCartRepo
import com.eCommerce.shopify.ui.shopping_cart.view_model.ShoppingCartViewModel
import com.eCommerce.shopify.ui.shopping_cart.view_model.ShoppingCartViewModelFactory

class ShoppingCartFragment : Fragment(), Listner {
    private var _binding: ShoppingCartFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ShoppingCartViewModel

    private val navController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }
    lateinit var adapter: ShopingCartAdapter

    companion object {
        fun newInstance() = ShoppingCartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ShoppingCartFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "Shopping Cart".also { binding.appBar.toolbar.title = it }
        binding.appBar.backArrow.setOnClickListener{
            navController.popBackStack()
        }
        var factory = ShoppingCartViewModelFactory(ShoppingCartRepo())
        viewModel = ViewModelProvider(this, factory).get(ShoppingCartViewModel::class.java)
        if(checkIfUserLoginAndInitInfo()){
            initShoppingCartRecyclerView()
            addCheckoutListener()
        }
        listenToLoginAndRegisterBtn()
    }

    private fun listenToLoginAndRegisterBtn(){
        listenToLoginBtn()
        listenToRegisterBtn()
    }

    private fun listenToLoginBtn(){
        binding.loginBtn.setOnClickListener {
            navController.navigate(R.id.action_shoppingCartFragment_to_loginFragment)
        }
    }
    private fun listenToRegisterBtn(){
        binding.registerBtn.setOnClickListener {
            navController.navigate(R.id.action_shoppingCartFragment_to_registerFragment)
        }
    }

    private fun checkIfUserLoginAndInitInfo(): Boolean {
        var isLogin: Boolean = viewModel.getIsLogin(requireContext())
        if(isLogin){
            binding.scNoLogin.visibility = View.GONE
            binding.scLogin.visibility = View.VISIBLE
            binding.shoppingCartPage.setBackgroundResource(R.color.titan_white)

        }
        else{
            binding.scNoLogin.visibility = View.VISIBLE
            binding.scLogin.visibility = View.GONE
            binding.shoppingCartPage.setBackgroundResource(R.color.white)
        }
        return isLogin
    }

    private fun addCheckoutListener() {
        binding.checkoutBtn.setOnClickListener {
            navController.navigate(R.id.action_shoppingCartFragment_to_checkoutFragment)
        }
    }

    fun initShoppingCartRecyclerView(){
        adapter = ShopingCartAdapter(this)
        _binding?.scRecyclerView?.layoutManager = LinearLayoutManager(this.requireContext()).apply {
            orientation =  LinearLayoutManager.VERTICAL
        }

        _binding?.scRecyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun update() {
        TODO("Not yet implemented")
    }

}