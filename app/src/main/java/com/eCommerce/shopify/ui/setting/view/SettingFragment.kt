package com.eCommerce.shopify.ui.setting.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.*
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.setting.repo.SettingRepo
import com.eCommerce.shopify.ui.setting.view_model.SettingViewModel
import com.eCommerce.shopify.ui.setting.view_model.SettingViewModelFactory

class SettingFragment : Fragment() {


    private lateinit var _binding: SettingFragmentBinding
    private val binding get() = _binding
    private lateinit var viewModel: SettingViewModel

    private val mNavController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SettingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = SettingViewModelFactory(SettingRepo(APIClient.getInstance()))
        viewModel = ViewModelProvider(this, factory)[SettingViewModel::class.java]
        if(checkIfUserLoginAndInitInfo()){
            listenToAllBtn()
        }
        listenToLoginAndRegisterBtn()

    }

    private fun listenToAllBtn(){
        listenToLogoutBtn()
        listenToAddressBtn()
        listenToCurrencyBtn()
        listenToContactUsBtn()
        listenToAboutUsBtn()
    }
    private fun listenToLoginAndRegisterBtn(){
        listenToLoginBtn()
        listenToRegisterBtn()
    }

    private fun listenToLoginBtn(){
        binding.loginBtn.setOnClickListener {
            mNavController.navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }
    private fun listenToRegisterBtn(){
        binding.registerBtn.setOnClickListener {
            mNavController.navigate(R.id.action_mainFragment_to_registerFragment2)
        }
    }
    private fun checkIfUserLoginAndInitInfo(): Boolean{
        val isLogin: Boolean = viewModel.getIsLogin(requireContext())
        if(isLogin){
            binding.settingNoLogin.visibility = View.GONE
            binding.loginLayout.visibility = View.VISIBLE
            binding.settingPage.setBackgroundResource(R.color.titan_white)
            binding.helloName.text = getString(R.string.hello).plus(viewModel.getUserName(requireContext()))
            binding.email.text = viewModel.getUserEmail(requireContext())

        }
        else{
            binding.settingNoLogin.visibility = View.VISIBLE
            binding.settingPage.setBackgroundResource(R.color.white)
            binding.loginLayout.visibility = View.GONE
        }
        return isLogin
    }
    private fun listenToLogoutBtn(){
        binding.logoutBtn.setOnClickListener {
            val inflater = requireActivity().layoutInflater
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val bind :ConfirmDialogBinding = ConfirmDialogBinding .inflate(inflater)
            dialog.setContentView(bind.root)
            dialog.setTitle("Confirmation")
            bind.confirmText.text = getString(R.string.sure_logout)
            bind.okBtn.setOnClickListener {
                viewModel.setIsLogin(requireContext(), false)
                checkIfUserLoginAndInitInfo()
                print("in ok button")
                dialog.dismiss()
            }
            bind.cancelBtn.setOnClickListener{
                print("in cancel button")
                dialog.dismiss()
            }

            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
    }
    private fun listenToAddressBtn(){
        binding.addressCardView.setOnClickListener {
            mNavController.navigate(R.id.action_mainFragment_to_addressesFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun listenToCurrencyBtn(){
        binding.currencyCardView.setOnClickListener {

            val currency = viewModel.getCurrencyFromSharedPref(requireContext())
            val inflater = requireActivity().layoutInflater
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val bind :CurrencyDialogBinding = CurrencyDialogBinding .inflate(inflater)
            dialog.setContentView(bind.root)
            dialog.setTitle("Change currency")

            bind.currentCurrencyText.text = "The currency now is $currency"

            bind.dollerBtn.setOnClickListener {
                if(currency == "$"){
                    confirmDialog("The currency already $ !!          ")
                }
                else{
                    Log.i("CURRENCY",currency)
                    confirmDialog("Are you sure you want to change currency to $ ?").observe(viewLifecycleOwner, {
                        if(it == true){
                            viewModel.setCurrencyToSharedPref(requireContext(), "$")
                            Log.i("CURRENCY", currency)
                        }
                    })
                }
                dialog.dismiss()
            }
            bind.egpBtn.setOnClickListener{
                if(viewModel.getCurrencyFromSharedPref(requireContext()) == "EGP"){
                    confirmDialog("The currency already EGP !!         ")
                }
                else{
                    Log.i("CURRENCY",viewModel.getCurrencyFromSharedPref(requireContext()))
                    confirmDialog("Are you sure you want to change currency to EGP ?").observe(viewLifecycleOwner, {
                        if(it == true){
                            viewModel.setCurrencyToSharedPref(requireContext(), "EGP")
                            Log.i("CURRENCY",viewModel.getCurrencyFromSharedPref(requireContext()))
                        }
                    })
                }
                dialog.dismiss()
            }
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
    }

    private fun listenToAboutUsBtn(){
        binding.aboutUsCardView.setOnClickListener{
            val inflater = requireActivity().layoutInflater
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val bind :AboutusDialogBinding = AboutusDialogBinding .inflate(inflater)
            dialog.setContentView(bind.root)
            bind.dismissBtn.setOnClickListener{
                dialog.dismiss()
            }
            dialog.setTitle("About Us")
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
    }
    private fun listenToContactUsBtn(){
        binding.contactusCardView.setOnClickListener{
            val inflater = requireActivity().layoutInflater
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val bind :ContactUsBinding = ContactUsBinding .inflate(inflater)
            dialog.setContentView(bind.root)
            bind.dismissBtn.setOnClickListener{
                dialog.dismiss()
            }
            dialog.setTitle("Contact Us")
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
    }

    private fun confirmDialog(title: String): LiveData<Boolean>{
        val isOk: MutableLiveData<Boolean> = MutableLiveData()
        val inflater = requireActivity().layoutInflater
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bind :ConfirmDialogBinding = ConfirmDialogBinding .inflate(inflater)
        dialog.setContentView(bind.root)
        dialog.setTitle("Confirmation")
        bind.confirmText.text = title
        bind.okBtn.setOnClickListener {
            with(isOk) { postValue(true) }
            Log.i("Confirm","in ok button")
            dialog.dismiss()
        }
        bind.cancelBtn.setOnClickListener{
            with(isOk) { postValue(false) }
            Log.i("Confirm","in cancel button")
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        return isOk
    }
}