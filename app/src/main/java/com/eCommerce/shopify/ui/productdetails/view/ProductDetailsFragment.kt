package com.eCommerce.shopify.ui.productdetails.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentProductDetailsBinding
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.ProductDetails
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.MainFragmentDirections
import com.eCommerce.shopify.ui.productdetails.repo.ProductDetailsRepo
import com.eCommerce.shopify.ui.productdetails.viewmodel.ProductDetailsViewModel
import com.eCommerce.shopify.ui.productdetails.viewmodel.ProductDetailsViewModelFactory
import com.eCommerce.shopify.ui.reviews.Review
import com.eCommerce.shopify.ui.reviews.ReviewsAdapter
import com.eCommerce.shopify.utils.AppConstants
import kotlin.math.abs

class ProductDetailsFragment : Fragment() {

    private lateinit var productDetailsViewModelFactory: ProductDetailsViewModelFactory
    private lateinit var viewModel: ProductDetailsViewModel
    private var _binding: FragmentProductDetailsBinding? = null

    private lateinit var myView: View

    private var sliderHandler = Handler(Looper.getMainLooper())

    private val mNavController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    private lateinit var sliderImages: List<ImageProduct>

    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var linearManager: LinearLayoutManager

    private val args by navArgs<ProductDetailsFragmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        gettingViewModelReady()
        handleUIActions()
        initRecyclerView()
    }

    private fun gettingViewModelReady() {
        productDetailsViewModelFactory = ProductDetailsViewModelFactory(
            ProductDetailsRepo.getInstance(
                APIClient.getInstance()
            )
        )
        viewModel = ViewModelProvider(this, productDetailsViewModelFactory)[ProductDetailsViewModel::class.java]

        viewModel.getCurrencyWithUserEmail(myView.context)
        viewModel.getCategoryProducts(args.categoryProductId)

        viewModel.errorMsgResponse.observe(viewLifecycleOwner, {
            AppConstants.showAlert(
                myView.context,
                R.string.error,
                it,
                R.drawable.ic_error
            )
        })
        viewModel.showProgressBar.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.productDetailsResponse.observe(viewLifecycleOwner) {
            renderDataOnScreen(it)
        }
        viewModel.currencyResponse.observe(viewLifecycleOwner) {
            binding.txtViewCurrency.text = it
        }
    }

    private fun renderDataOnScreen(it: ProductDetails) {
        handleUIViewPager(it.product.images)
        binding.txtViewProductName.text = it.product.title
        binding.txtViewProductPrice.text = it.product.variants[0].price
        binding.txtViewDescription.text = it.product.bodyHtml

        val reviewsList = listOf(
            Review("hana",3.0,"https://image.shutterstock.com/image-photo/profile-picture-smiling-millennial-asian-260nw-1836020740.jpg","ya3ni mesh 7elw awi"),
            Review("7ala",3.5,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOUbxyj7wgPREiPY2ApXyrI0P7sg-v30CY1g&usqp=CAU","7elw nos nos hhhhhhh"),
            Review("soha",4.0,"https://wallpapercave.com/wp/wp7810667.jpg","yalla kowais w 5alas"),
        )

        reviewsAdapter.setDataToAdapter(reviewsList)
    }

    private fun handleUIActions() {
        binding.cardViewIsFavorite.setOnClickListener {

        }

        binding.btnReviewsMore.setOnClickListener {
            val action = ProductDetailsFragmentDirections.actionProductDetailsFragmentToReviewsFragment()
            mNavController.navigate(action)
        }

        binding.btnReviewsAddToBag.setOnClickListener {

        }
    }

    private fun initRecyclerView() {
        reviewsAdapter = ReviewsAdapter(myView.context, emptyList())
        linearManager = LinearLayoutManager(myView.context, RecyclerView.VERTICAL, false)
        binding.reviewsRecycler.apply {
            adapter = reviewsAdapter
            layoutManager = linearManager
        }
    }

    private fun handleUIViewPager(images: List<ImageProduct>) {
        sliderImages = images
        binding.viewPagerAdsSlider.adapter = SliderProductDetailsAdapter(myView.context, images)
        binding.viewPagerAdsSlider.clipToPadding = false
        binding.viewPagerAdsSlider.clipChildren = false
        binding.viewPagerAdsSlider.offscreenPageLimit = 3
        binding.viewPagerAdsSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        binding.viewPagerAdsSlider.setPageTransformer(compositePageTransformer)
        binding.viewPagerAdsSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })
    }

    private var sliderRunnable = Runnable {
        if (binding.viewPagerAdsSlider.currentItem + 1 >= sliderImages.size) {
            binding.viewPagerAdsSlider.currentItem = 0
        } else {
            binding.viewPagerAdsSlider.currentItem++
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}