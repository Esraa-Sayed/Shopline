package com.eCommerce.shopify.ui.login.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.LoginFragmentBinding
import com.eCommerce.shopify.ui.login.viewModel.LoginViewModel

class LoginFragment : Fragment() {


    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.email
        binding.password
        binding.createAccount
        binding.logIn.setOnClickListener {
            if (binding.email.text.isNullOrEmpty())
            {
                binding.email.error = "required"
            }
            if (binding.password.text.isNullOrEmpty())
            {
                binding.password.error = "required"
            }
        }
        binding.createAccount.setOnClickListener {
            Log.e("TAG", "onViewCreated: createAccount" )
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}