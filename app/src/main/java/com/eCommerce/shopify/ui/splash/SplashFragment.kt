package com.eCommerce.shopify.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentHomeBinding
import com.eCommerce.shopify.databinding.FragmentMainBinding
import com.eCommerce.shopify.databinding.FragmentSplashBinding
import com.eCommerce.shopify.ui.MainFragment
import com.eCommerce.shopify.utils.AppConstants

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController(view).navigate(R.id.action_splashFragment_to_mainFragment)
        }, AppConstants.SPLASH_TIME_OUT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}