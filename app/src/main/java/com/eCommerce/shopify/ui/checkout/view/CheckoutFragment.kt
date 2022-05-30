package com.eCommerce.shopify.ui.checkout.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eCommerce.shopify.databinding.CheckoutFragmentBinding
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModel

class CheckoutFragment : Fragment() {


    private lateinit var viewModel: CheckoutViewModel
    private lateinit var binding: CheckoutFragmentBinding
    private lateinit var myView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CheckoutFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myView = view
        init()

    }
    fun init(){
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        binding.appBar.toolbar.title = "Checkout"
    }
}