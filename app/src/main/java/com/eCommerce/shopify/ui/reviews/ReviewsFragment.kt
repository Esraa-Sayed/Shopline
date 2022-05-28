package com.eCommerce.shopify.ui.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentReviewsBinding

class ReviewsFragment : Fragment() {

    private lateinit var binding:FragmentReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewsBinding.inflate(inflater,container,false)
        return binding.root
    }

}