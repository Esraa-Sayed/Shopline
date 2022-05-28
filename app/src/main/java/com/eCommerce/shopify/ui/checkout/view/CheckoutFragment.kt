package com.eCommerce.shopify.ui.checkout.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eCommerce.shopify.R
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModel

class CheckoutFragment : Fragment() {

    companion object {
        fun newInstance() = CheckoutFragment()
    }

    private lateinit var viewModel: CheckoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.checkout_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        // TODO: Use the ViewModel
    }

}