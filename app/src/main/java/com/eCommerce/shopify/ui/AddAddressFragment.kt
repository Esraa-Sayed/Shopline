package com.eCommerce.shopify.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eCommerce.shopify.R

class AddAddressFragment : Fragment() {

    companion object {
        fun newInstance() = AddAddressFragment()
    }

    private lateinit var viewModel: AddAddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.add_address_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddAddressViewModel::class.java)
        // TODO: Use the ViewModel
    }

}