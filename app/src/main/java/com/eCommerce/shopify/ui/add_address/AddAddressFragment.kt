package com.eCommerce.shopify.ui.add_address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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