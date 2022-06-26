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
            Review("Emad Nashaat",2.5,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSa69_HGc_i3MXKCPZzCfAjBZC4bXJsn0rS0Ufe6H-ctZz5FbIVaPkd1jCPTpKwPruIT3Q&usqp=CAU","the material wasn't good"),
            Review("Asmaa Galal",3.0,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5WYwjOWQCZ3g-4zrImh6lIDS7q50T4sE7S-_OeljDtY3M6pqAYqFNpKzi-t33hglOahk&usqp=CAU","good product but the packaging was bad"),
            Review("Hoda Ahmed",4.0,"https://cdn3.vectorstock.com/i/1000x1000/88/92/face-young-woman-in-frame-circular-avatar-vector-28828892.jpg","Very good quality like the description"),
            Review("Saef Mohamed",4.5,"https://static.vecteezy.com/system/resources/previews/004/477/337/non_2x/face-young-man-in-frame-circular-avatar-character-icon-free-vector.jpg","Nice product and wonderful price"),
            Review("Anas Elhady",3.5,"https://library.kissclipart.com/20180919/xae/kissclipart-male-avatar-icon-clipart-computer-icons-avatar-104635e37f6b2f94.png","I wear these to work all day. They are comfortable. They look cool and size fits well as expected."),
            Review("Roqaya Ali",4.0,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQ2VgiLlAKs-BEY4sd-qiTehWu3Ke59p2_n7twjMLSFbANDQ5n-LTlc7vvX_VWrrFAc4I&usqp=CAU","Great product and equally impressive customer services, very impressed with this transaction. Thanks"),
            Review("Sahar Samy",1.0,"https://img.freepik.com/free-vector/woman-profile-cartoon_18591-58477.jpg","tight on the shoes, hurts after wearing it for an hour"),
            Review("John Manuel",3.0,"https://www.nicepng.com/png/detail/174-1748863_jack-bayley-avatar-vector-icon-boy.png","Look almost repaired. Glue around front of trainers And large size 8 almost a 9")
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