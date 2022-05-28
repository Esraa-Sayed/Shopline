package com.eCommerce.shopify.ui.addresses.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.AddressesFragmentBinding
import com.eCommerce.shopify.model.address
import com.eCommerce.shopify.ui.addresses.viewModel.AddressesViewModel

class AddressesFragment : Fragment() {

    private lateinit var viewModel: AddressesViewModel
    private lateinit var binding: AddressesFragmentBinding
    private lateinit var myView: View
    private lateinit var addressesAdapter: AddressesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddressesFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myView = view
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(AddressesViewModel::class.java)
        binding.addAddress.setOnClickListener {
            Log.e("TAG", "init: add address clicked" )
        }

        val addresses = listOf<address>(address("Egypt","Cairo,Helwan"),address("Eqypt","M,R"))
        addressesAdapter = AddressesAdapter(myView.context, addresses)
        val layoutManag = LinearLayoutManager(activity)
        binding.addressesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManag.orientation = RecyclerView.VERTICAL
            layoutManager = layoutManag
            adapter = addressesAdapter
        }
    }

}