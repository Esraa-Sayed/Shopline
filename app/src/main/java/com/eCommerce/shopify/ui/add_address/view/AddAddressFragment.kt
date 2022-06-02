package com.eCommerce.shopify.ui.add_address.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.AddAddressFragmentBinding
import com.eCommerce.shopify.ui.add_address.view_model.AddAddressViewModel
import com.eCommerce.shopify.ui.setting.repo.AddAddressRepo

class AddAddressFragment : Fragment() {

    companion object {
        fun newInstance() = AddAddressFragment()
    }

    private lateinit var _binding: AddAddressFragmentBinding
    private val binding get() = _binding
    private lateinit var viewModel: AddAddressViewModel
    private val mNavController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.add_address_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddAddressViewModel::class.java)
        viewModel.setRepo(AddAddressRepo())
    }

    fun listenToAddAddressButton(){

    }


}