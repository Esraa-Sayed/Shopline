package com.eCommerce.shopify.ui.addresses.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.ui.AddressAndCheckoutAdapter.AddressesAdapter
import com.eCommerce.shopify.databinding.AddressesFragmentBinding
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.addresses.repo.AddressesRepo
import com.eCommerce.shopify.ui.addresses.viewModel.AddressesViewModel
import com.eCommerce.shopify.ui.addresses.viewModel.AddressesViewModelFactory
import com.eCommerce.shopify.ui.order.repo.OrdersRepo
import com.eCommerce.shopify.ui.order.viewModel.OrdersViewModel
import com.eCommerce.shopify.ui.order.viewModel.OrdersViewModelFactory
import com.eCommerce.shopify.utils.AppConstants

class AddressesFragment : Fragment() {

    private lateinit var viewModel: AddressesViewModel
    private lateinit var addressesViewModelFactory: AddressesViewModelFactory
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
        getString(R.string.addressTitle).also { binding.appBar.toolbar.title = it }
        addressesViewModelFactory = AddressesViewModelFactory(
            AddressesRepo.getInstance(
                APIClient.getInstance()
            )
        )
        viewModel = ViewModelProvider(this, addressesViewModelFactory)[AddressesViewModel::class.java]
        binding.addAddress.setOnClickListener {
            Log.e("TAG", "init: add address clicked" )
            findNavController().navigate(R.id.action_addressesFragment_to_addAddressFragment)
        }
        addressesAdapter = AddressesAdapter(myView.context, emptyList(),null)
        val layoutManag = LinearLayoutManager(activity)
        binding.addressesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManag.orientation = RecyclerView.VERTICAL
            layoutManager = layoutManag
            adapter = addressesAdapter
        }
        getUserAddresses()
    }
    private fun getUserAddresses() {
        viewModel.getUserAddresses(myView.context)
        viewModel.UserAddresses.observe(viewLifecycleOwner, Observer {
            if (it.addresses.isNotEmpty()){
                dataFound()
                addressesAdapter.updateData(it.addresses)
            }else{
                noDataFound()
            }
            Log.e("TAG", "getUserOrders: ${it.addresses.size}" )
        })
        viewModel.errorMsgResponse.observe(viewLifecycleOwner, Observer {
            AppConstants.showAlert(
                myView.context,
                R.string.error,
                it,
                R.drawable.ic_error
            )
        })
    }
    private fun noDataFound(){
        binding.noAddressFound.visibility = View.VISIBLE
        binding.noAddressFoundText.visibility = View.VISIBLE
        binding.addressesRecyclerView.visibility = View.GONE
    }
    private fun dataFound(){
        binding.noAddressFound.visibility = View.GONE
        binding.noAddressFoundText.visibility = View.GONE
        binding.addressesRecyclerView.visibility = View.VISIBLE
    }

}