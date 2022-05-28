package com.eCommerce.shopify.ui.shopping_cart.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eCommerce.shopify.databinding.ShoppingCartFragmentBinding
import com.eCommerce.shopify.ui.shopping_cart.view_model.ShoppingCartViewModel

class ShoppingCartFragment : Fragment(), Listner {

    private var _binding: ShoppingCartFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ShoppingCartViewModel
    lateinit var adapter: ShopingCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //var view = inflater.inflate(R.layout.shopping_cart_fragment, container, false)
        _binding = ShoppingCartFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShoppingCartViewModel::class.java)
        initShoppingCartRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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