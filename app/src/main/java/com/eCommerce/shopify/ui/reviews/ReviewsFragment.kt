package com.eCommerce.shopify.ui.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentReviewsBinding

class ReviewsFragment : Fragment() {

    private lateinit var binding:FragmentReviewsBinding
    private lateinit var myView: View
    private lateinit var navController: NavController

    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var linearManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReviewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        this.navController = findNavController()
        setupToolbar()
        initRecyclerView()

        val reviewsList = listOf(
            Review("hana",3.0,"https://image.shutterstock.com/image-photo/profile-picture-smiling-millennial-asian-260nw-1836020740.jpg","ya3ni mesh 7elw awi"),
            Review("7ala",3.5,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOUbxyj7wgPREiPY2ApXyrI0P7sg-v30CY1g&usqp=CAU","7elw nos nos hhhhhhh"),
            Review("soha",4.0,"https://wallpapercave.com/wp/wp7810667.jpg","yalla kowais w 5alas"),
            Review("nour",4.5,"https://i.pinimg.com/736x/d8/ea/1b/d8ea1be3acc5102e993e8b1780f6a569.jpg","aho ay 7aga w el salam")
        )

        reviewsAdapter.setDataToAdapter(reviewsList)
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.appBar.toolbar)

        binding.appBar.toolbar.title = getString(R.string.reviews)
        binding.appBar.backArrow.setOnClickListener { navController.popBackStack() }
    }

    private fun initRecyclerView() {
        reviewsAdapter = ReviewsAdapter(myView.context, emptyList())
        linearManager = LinearLayoutManager(myView.context, RecyclerView.VERTICAL, false)
        binding.reviewsRecycler.apply {
            adapter = reviewsAdapter
            layoutManager = linearManager
        }
    }
}