package com.eCommerce.shopify.ui.profile.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.ProfileFragmentBinding
import com.eCommerce.shopify.ui.profile.view_model.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private lateinit var _binding: ProfileFragmentBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    lateinit var ordersAdapter: OrdersAdapter
    lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWishlistRecyclerView()
        initOrdersRecyclerView()
    }

    fun initOrdersRecyclerView(){
        ordersAdapter = OrdersAdapter()
        _binding?.pOrdersRecyclerView?.layoutManager = LinearLayoutManager(this.requireContext()).apply {
            orientation =  LinearLayoutManager.VERTICAL
        }

        _binding?.pOrdersRecyclerView?.adapter = ordersAdapter
    }
    fun initWishlistRecyclerView(){
        wishlistAdapter = WishlistAdapter()
        _binding?.pWishlistRecyclerView?.layoutManager = GridLayoutManager(this.requireContext(), 2)

        _binding?.pWishlistRecyclerView?.adapter = wishlistAdapter
    }

}