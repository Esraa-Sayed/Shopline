package com.eCommerce.shopify.ui.home.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentHomeBinding
import com.eCommerce.shopify.model.SliderItem
import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.home.repo.HomeRepo
import com.eCommerce.shopify.ui.home.viewmodel.HomeViewModel
import com.eCommerce.shopify.ui.home.viewmodel.HomeViewModelFactory
import com.eCommerce.shopify.utils.AppConstants.showAlert
import kotlin.math.abs

class HomeFragment : Fragment() {

    private lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

//    private lateinit var homeAdapter: HomeAdapter
//    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var myView: View
    private val sliderItems = mutableListOf(
        SliderItem(R.drawable.banner1),
        SliderItem(R.drawable.banner2),
        SliderItem(R.drawable.banner3)
    )
    private var sliderHandler = Handler(Looper.getMainLooper())

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        gettingViewModelReady()
        handleUIViewPager()
    }

    private fun gettingViewModelReady() {
        homeViewModelFactory = HomeViewModelFactory(
            HomeRepo.getInstance(
                APIClient.getInstance()
            )
        )
        viewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]

        viewModel.getSmartCollectionsBrand()

        viewModel.errorMsgResponse.observe(viewLifecycleOwner, {
            showAlert(
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
        viewModel.smartCollectionsBrandResponse.observe(viewLifecycleOwner, {
            renderDataOnScreen(it)
        })
    }

    private fun renderDataOnScreen(it: SmartCollectionsBrand) {

    }

    private fun handleUIViewPager() {
        binding.viewPagerAdsSlider.adapter = SliderAdapter(sliderItems)
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
        if (binding.viewPagerAdsSlider.currentItem + 1 > 2) {
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