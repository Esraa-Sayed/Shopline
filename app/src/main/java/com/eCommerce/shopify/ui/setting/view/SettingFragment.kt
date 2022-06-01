package com.eCommerce.shopify.ui.setting.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.*
import com.eCommerce.shopify.ui.setting.repo.SettingRepo
import com.eCommerce.shopify.ui.setting.view_model.SettingViewModel

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

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
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        viewModel.setRepo(SettingRepo())
        checkIfUserLogin()
        listenToAllBtn()
    }
    private fun listenToAllBtn(){
        listenToLoginBtn()
        listenToRegisterBtn()
        listenToLogoutBtn()
        listenToAddressBtn()
        listenToCurrencyBtn()
        listenToContactUsBtn()
        listenToAboutUsBtn()
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
    private fun checkIfUserLogin() {
        if(viewModel.getIsLogin(requireContext())){
            binding.settingNoLogin.visibility = View.GONE
            binding.settingLogin.visibility = View.VISIBLE
        }
        else{
            binding.settingNoLogin.visibility = View.VISIBLE
            binding.settingLogin.visibility = View.GONE
        }
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
            bind.confirmText.text = "Are you sure you want to logout?"
            bind.okBtn.setOnClickListener {
                viewModel.setIsLogin(requireContext(), false)
                checkIfUserLogin()
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

    private fun listenToCurrencyBtn(){
        binding.currencyCardView.setOnClickListener {

            val inflater = requireActivity().layoutInflater
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val bind :CurrencyDialogBinding = CurrencyDialogBinding .inflate(inflater)
            dialog.setContentView(bind.root)
            dialog.setTitle("Change currency")

            bind.dollerBtn.setOnClickListener {
                confirmDialog("Are you sure you want to change currency to $ ?")

                //change customer currency here
                dialog.dismiss()
            }
            bind.egpBtn.setOnClickListener{
                confirmDialog("Are you sure you want to change currency to EGP ?")
                //change customer currency here
                dialog.dismiss()
            }
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
    }

    private fun listenToAboutUsBtn(){
        binding.aboutusCardView.setOnClickListener{
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

    private fun confirmDialog(title: String): Boolean{
        var isOk: Boolean = false
        val inflater = requireActivity().layoutInflater
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bind :ConfirmDialogBinding = ConfirmDialogBinding .inflate(inflater)
        dialog.setContentView(bind.root)
        dialog.setTitle("Confirmation")
        bind.confirmText.text = title
        bind.okBtn.setOnClickListener {
            isOk = true
            print("in ok button")
            dialog.dismiss()
        }
        bind.cancelBtn.setOnClickListener{
            isOk = false
            print("in cancel button")
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        return isOk
    }
}


//val builder = AlertDialog.Builder(it)
//alert confirm dialog
//        val alertDialog: AlertDialog? = activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.apply {
//
//                setView(inflater.inflate(R.layout.confirm_dialog, null))
//                setTitle(title)
//                setPositiveButton(R.string.ok,
//                    DialogInterface.OnClickListener { dialog, id ->
//                        isOk = true
//                    })
//                setNegativeButton(getString(R.string.cancel),
//                    DialogInterface.OnClickListener { dialog, id ->
//                        isOk = false
//                    })
//            }.show()
//            builder.create()
//        }
