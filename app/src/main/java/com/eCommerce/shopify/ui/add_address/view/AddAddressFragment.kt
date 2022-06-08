package com.eCommerce.shopify.ui.add_address.view

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.AddAddressFragmentBinding
import com.eCommerce.shopify.databinding.ConfirmDialogBinding
import com.eCommerce.shopify.model.Addresse
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.model.PostAddress
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.add_address.view_model.AddAddressViewModel
import com.eCommerce.shopify.ui.add_address.view_model.AddAddressViewModelFactory
import com.eCommerce.shopify.ui.setting.repo.AddAddressRepo
import com.eCommerce.shopify.utils.AppConstants
import android.content.DialogInterface




class AddAddressFragment : Fragment() {

    companion object {
        fun newInstance() = AddAddressFragment()
    }

    private lateinit var _binding: AddAddressFragmentBinding
    private val binding get() = _binding
    private lateinit var viewModel: AddAddressViewModel

    private val navController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = AddAddressFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "Add address".also { binding.appBar.toolbar.title = it }
        binding.appBar.backArrow.setOnClickListener{
            navController.popBackStack()
        }
        val factory = AddAddressViewModelFactory(AddAddressRepo.getInstance(APIClient.getInstance()))
        viewModel = ViewModelProvider(this, factory)[AddAddressViewModel::class.java]
        listenToAddAddressButton()

        viewModel.errorMsgResponse.observe(viewLifecycleOwner, {
            errorDialog(it)
            binding.errorMsg.visibility = View.VISIBLE
            AppConstants.showAlert(requireContext(), 2, it, 2)
        })
    }

    private fun listenToAddAddressButton(){

        binding.addAddress.setOnClickListener{
            if(binding.addressOneName.text.isNullOrEmpty() || binding.addressTwoName.text.isNullOrEmpty() ||
                binding.cityName.text.isNullOrEmpty() ||  binding.countryName.text.isNullOrEmpty() ||
                binding.firstName.text.isNullOrEmpty() || binding.lastName.text.isNullOrEmpty()){
                binding.errorMsg.visibility = View.VISIBLE
            }
            else{
                binding.errorMsg.visibility = View.GONE
                viewModel.addAddress(requireContext(), PostAddress(Addresse(address1 = binding.addressOneName.text.toString(), address2 = binding.addressTwoName.text.toString(),
                    city = binding.cityName.text.toString(), country = binding.countryName.text.toString(),
                    first_name = binding.firstName.text.toString(), last_name = binding.lastName.text.toString() )))
            }
        }
    }

    private fun errorDialog(msg: String){
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Error message")
            .setMessage(msg)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .show()
    }
}