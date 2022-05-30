package com.eCommerce.shopify.ui.shopping_cart.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.ProfileFragmentBinding
import com.eCommerce.shopify.databinding.ShoppingCartFragmentBinding
import com.eCommerce.shopify.ui.shopping_cart.view_model.ShoppingCartViewModel

class ShoppingCartFragment : Fragment(), Listner {
    private var _binding: ShoppingCartFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var viewModel: ShoppingCartViewModel
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShoppingCartViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initShoppingCartRecyclerView()
        addCheckoutListener()
    }

    private fun addCheckoutListener() {
        this.navController = findNavController()
        binding.shCheckoutCard.setOnClickListener {
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