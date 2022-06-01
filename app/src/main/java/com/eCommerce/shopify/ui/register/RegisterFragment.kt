package com.eCommerce.shopify.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentBrandProductsBinding
import com.eCommerce.shopify.databinding.FragmentRegisterBinding
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.brandproducts.view.BrandProductsAdapter
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModel
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModelFactory

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerViewModelFactory: RegisterViewModelFactory
    private lateinit var navController: NavController
    private var incommingCustomer:CustomerResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.navController = findNavController()

        registerViewModelFactory = RegisterViewModelFactory(RegisterRepo.getInstance(APIClient.getInstance()))
        registerViewModel = ViewModelProvider(this,registerViewModelFactory).get(RegisterViewModel::class.java)

        binding.registerBtn.setOnClickListener {
            registrationHandling()
        }

    }

    fun registrationHandling(){
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
            password.length < 8 -> binding.passwordInRegister.error = getString(R.string.passwordWarning)
            confirm.toString() != password.toString() -> binding.confirmPassInRegister.error = getString(R.string.confirmPasswordWarning)
            else -> validUserData = true
        }

        if(validUserData) {
            val subIncommingCustomer = Customer()
            subIncommingCustomer.first_name = fName.toString()
            subIncommingCustomer.last_name = lName.toString()
            subIncommingCustomer.email = email.toString()
            subIncommingCustomer.phone = ""
            subIncommingCustomer.verified_email = true
            subIncommingCustomer.tags = password.toString()

            incommingCustomer = CustomerResponse(subIncommingCustomer)

            registerViewModel.errorMsgResponse.observe(viewLifecycleOwner){
                Log.i("TAG", "errrrrrrrrrrrror in post user!!!!!!!")
            }
            registerViewModel.postNewCustomer(incommingCustomer as CustomerResponse)
            registerViewModel.customerRespoonse.observe(viewLifecycleOwner) {
                if(!it.customer.email.isNullOrEmpty()) {
                    Log.i("TAG", "register ssssssuccessssssssssssfulllllll " + it.customer.email)
                    val goToHome = RegisterFragmentDirections.actionRegisterFragmentToMainFragment(it.customer)
                    navController.navigate(goToHome)
                }
                else{
                    Log.i("TAG", "Response is null or empety!!!!!")
                }
            }
        }
    }

}