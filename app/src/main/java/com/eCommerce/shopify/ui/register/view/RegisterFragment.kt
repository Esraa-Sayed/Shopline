package com.eCommerce.shopify.ui.register.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentRegisterBinding
import com.eCommerce.shopify.databinding.GoToLoginDialogBinding
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.model.EmailMarketingConsent
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.register.repo.RegisterRepo
import com.eCommerce.shopify.ui.register.viewmodel.RegisterViewModel
import com.eCommerce.shopify.ui.register.viewmodel.RegisterViewModelFactory
import com.eCommerce.shopify.utils.AppConstants

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var myView: View
    private lateinit var navController: NavController

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerViewModelFactory: RegisterViewModelFactory

    private var incommingCustomer: CustomerResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TAG", "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.i("TAG", "onCreateView: ")
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("TAG", "onViewCreated: ")
        this.myView = view
        this.navController = findNavController()
        setupViewModel()

        binding.registerBtn.setOnClickListener {
            registrationHandling()
        }
        binding.goToLoginLinear.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.backInRegister.setOnClickListener {
            navController.popBackStack()
        }

        registerViewModel.errorMsgResponse.observe(viewLifecycleOwner){
            Log.i("TAG", "errrrrrrrrrrrror in post user!!!!!")
            //registerViewModel.errorMsgResponse.removeObservers(viewLifecycleOwner)
            showErrorMessage(it)
            //registerViewModel.errorMsgResponse.removeObservers(viewLifecycleOwner)
        }

        registerViewModel.customerRespoonse.observe(viewLifecycleOwner) {
            if(!it.customer.email.isNullOrEmpty()) {
                Log.i("TAG", "register ssssssuccessssssssssssfulllllllyyy " + it.customer.email)
                registerViewModel.saveDataInSharedPref(myView.context,it.customer.email!!,it.customer.id!!,it.customer.first_name!!)

                val inflater = requireActivity().layoutInflater
                val dialog = Dialog(requireActivity())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val bind : GoToLoginDialogBinding = GoToLoginDialogBinding.inflate(inflater)
                dialog.setContentView(bind.root)
                dialog.setTitle(getString(R.string.warning))
                bind.warningTitle.text = getString(R.string.register_successfully)
                bind.okBtn.setOnClickListener {
                    navController.navigate(R.id.action_registerFragment_to_mainFragment)
                    dialog.dismiss()
                }
                bind.goToLogin.visibility = View.GONE
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
            }
            else{
                Log.i("TAG", "Response is null or empety!!!!!")
                showErrorMessage("there is some invalid data!")
            }
        }
    }

    fun setupViewModel(){
        Log.i("TAG", "setupViewModel: ")
        registerViewModelFactory = RegisterViewModelFactory(RegisterRepo.getInstance(APIClient.getInstance()))
        registerViewModel = ViewModelProvider(this,registerViewModelFactory).get(RegisterViewModel::class.java)
    }

    fun registrationHandling(){
        Log.i("TAG", "registrationHandling: ")
        var fName = binding.username.text
        var lName = binding.username.text
        var email = binding.emailInRegister.text
        var phone = binding.phoneInRegister.text
        var password = binding.passwordInRegister.text
        var confirm = binding.confirmPassInRegister.text
        var validUserData = false
        when{
            fName.isNullOrEmpty() -> binding.username.error = getString(R.string.required)
            email.isNullOrEmpty() -> binding.emailInRegister.error = getString(R.string.required)
            phone.isNullOrEmpty() -> binding.phoneInRegister.error = getString(R.string.required)
            password.isNullOrEmpty() -> binding.passwordInRegister.error = getString(R.string.required)
            confirm.isNullOrEmpty() -> binding.confirmPassInRegister.error = getString(R.string.required)
            phone.length < 11 -> binding.phoneInRegister.error = getString(R.string.phone_validation)
            password.length < 8 -> binding.passwordInRegister.error = getString(R.string.passwordWarning)
            confirm.toString() != password.toString() -> binding.confirmPassInRegister.error = getString(R.string.confirmPasswordWarning)
            else -> validUserData = true
        }
        if(validUserData) {

            val subIncommingCustomer = Customer()
            subIncommingCustomer.first_name = fName.toString()
            subIncommingCustomer.last_name = lName.toString()
            subIncommingCustomer.email = email.toString()
            subIncommingCustomer.phone = phone.toString()
            subIncommingCustomer.verified_email = true
            subIncommingCustomer.tags = password.toString()
            incommingCustomer = CustomerResponse(subIncommingCustomer)

            Log.i("TAG", "registrationHandling:105")

            registerViewModel.postNewCustomer(incommingCustomer as CustomerResponse)
        }
    }

    fun showErrorMessage(message: String){
        Log.i("TAG", "showErrorMessage: ")
        AppConstants.showAlert(
            myView.context,
            R.string.error,
            message,
            R.drawable.ic_error
        )
    }
}