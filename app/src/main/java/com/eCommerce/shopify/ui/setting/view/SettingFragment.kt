package com.eCommerce.shopify.ui.setting.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.ProfileFragmentBinding
import com.eCommerce.shopify.databinding.SettingFragmentBinding
import com.eCommerce.shopify.ui.setting.view_model.SettingViewModel

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private lateinit var _binding: SettingFragmentBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = SettingFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}