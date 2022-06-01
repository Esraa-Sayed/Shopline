package com.eCommerce.shopify.ui.login.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.LoginFragmentBinding
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.home.repo.HomeRepo
import com.eCommerce.shopify.ui.home.viewmodel.HomeViewModel
import com.eCommerce.shopify.ui.home.viewmodel.HomeViewModelFactory
import com.eCommerce.shopify.ui.login.repo.LoginRepo
import com.eCommerce.shopify.ui.login.viewModel.LoginViewModel
import com.eCommerce.shopify.ui.login.viewModel.LoginViewModelFactory
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref

class LoginFragment : Fragment() {


    private lateinit var viewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var binding: LoginFragmentBinding
    private lateinit var myView: View
    private lateinit var loginButton: Button
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myView = view
        init()
    }
    fun init(){
        this.navController = findNavController()
        loginViewModelFactory = LoginViewModelFactory(
            LoginRepo.getInstance(
                APIClient.getInstance()
            )
        )
        loginButton = myView.findViewById(R.id.logIn)
        viewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
        binding.logIn.setOnClickListener {
            var flag= true
            if (binding.email.text.isNullOrEmpty())
            {
                binding.email.error = getString(R.string.required)
                flag = false
            }
            if (binding.password.text.isNullOrEmpty())
            {
                binding.password.error = getString(R.string.required)
                flag = false
            }
            if(flag){
                setLoginButtonEnableOrDisable(false)
                getUserData( binding.email.text.toString(),binding.password.text.toString())
            }
        }
        binding.createAccount.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
            Log.e("TAG", "onViewCreated: createAccount" )
        }
    }
    private fun getUserData(email: String, password: String) {
        viewModel.errorMsgResponse.observe(viewLifecycleOwner, {
            //setLoginButtonEnableOrDisable(true)
            AppConstants.showAlert(
                myView.context,
                R.string.error,
                it,
                R.drawable.ic_error
            )
        })
        viewModel.getUserData(email)
        viewModel.UserDataResponse.observe(viewLifecycleOwner){

            if(it.customers.isNotEmpty()){
                viewModel.UserDataResponse.removeObservers(viewLifecycleOwner)
                if(it.customers[0].tags == password){
                    saveDataInSharedPref(email,it.customers[0].id as Long)
                }
                else{
                    showErrorMessage(getString(R.string.EmailOrPasswordIsIncorrect))
                    setLoginButtonEnableOrDisable(true)
                }
            }
            else{
                showErrorMessage(getString(R.string.EmailOrPasswordIsIncorrect))
            }
        }
    }

    private fun saveDataInSharedPref(email: String, userId: Long) {
        val fileSharedPref = AppSharedPref.getInstance(myView.context,AppConstants.PREFRENCE_File)
        fileSharedPref.setValue(AppConstants.IS_LOGIN,true)
        fileSharedPref.setValue(AppConstants.USER_EMAIL,email)
        fileSharedPref.setValue(AppConstants.USER_ID,userId)
    }
    private fun showErrorMessage(message: String){
        AppConstants.showAlert(
            myView.context,
            R.string.error,
            message,
            R.drawable.ic_error
        )
    }
    private fun setLoginButtonEnableOrDisable(flag:Boolean){
        loginButton.isEnabled= flag
    }

}