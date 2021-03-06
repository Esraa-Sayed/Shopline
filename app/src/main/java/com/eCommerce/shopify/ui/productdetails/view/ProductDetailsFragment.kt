package com.eCommerce.shopify.ui.productdetails.view

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.favorite.LocalSource
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.databinding.FragmentProductDetailsBinding
import com.eCommerce.shopify.databinding.GoToLoginDialogBinding
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.ProductDetails
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.productdetails.repo.ProductDetailsRepo
import com.eCommerce.shopify.ui.productdetails.viewmodel.ProductDetailsViewModel
import com.eCommerce.shopify.ui.productdetails.viewmodel.ProductDetailsViewModelFactory
import com.eCommerce.shopify.ui.reviews.Review
import com.eCommerce.shopify.ui.reviews.ReviewsAdapter
import com.eCommerce.shopify.utils.AppConstants.showAlert
import com.eCommerce.shopify.utils.AppConstants.showDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class ProductDetailsFragment : Fragment(), OnImageClickListener {

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

    private var isFavoriteProduct = false
    private var isAddingToShoppingCart = false
    private lateinit var product: Product
    private lateinit var productDetail: ProductDetail

    private lateinit var dialog: Dialog

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
        setupToolbar()
        gettingViewModelReady()
        handleUIActions()
        initRecyclerView()
    }

    private fun setupToolbar() {
        binding.appBarHome.toolbar.title = getString(R.string.product_details)
    }

    private fun gettingViewModelReady() {
        productDetailsViewModelFactory = ProductDetailsViewModelFactory(
            ProductDetailsRepo.getInstance(
                APIClient.getInstance(), LocalSource(myView.context), ShoppingCartLocalSource(myView.context)
            )
        )
        viewModel = ViewModelProvider(this, productDetailsViewModelFactory)[ProductDetailsViewModel::class.java]

        viewModel.getCategoryProducts(myView.context, args.categoryProductId)

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
        viewModel.productDetailsResponse.observe(viewLifecycleOwner) {
            renderDataOnScreen(it)
            if(viewModel.isUserLogin(myView.context)){
                viewModel.getFavoriteProduct(it.product.id,viewModel.getUserId(myView.context)).observe(viewLifecycleOwner) { product ->
                    if (product == null) {
                        handleFavorite(false, R.drawable.ic_favorite_border)
                    } else {
                        handleFavorite(true, R.drawable.ic_favorite)
                    }
                }
            }
            else{
                handleFavorite(false, R.drawable.ic_favorite_border)
            }
            viewModel.getProductInShoppingCart(it.product.id).observe(viewLifecycleOwner) { productInBag ->
                if (productInBag == null) {
                    handleBag(false, getString(R.string.add_to_bag))
                } else {
                    handleBag(true, getString(R.string.remove_from_bag))
                }
            }
        }
        /*viewModel.currencyResponse.observe(viewLifecycleOwner) {
            binding.txtViewCurrency.text = it
        }*/
    }

    private fun renderDataOnScreen(it: ProductDetails) {
        productDetail = it.product
        product = Product(it.product.adminGraphqlApiId, it.product.bodyHtml, it.product.createdAt,
            it.product.handle, it.product.id, it.product.userId, it.product.image, it.product.images,
            it.product.options, it.product.productType, it.product.publishedAt,
            it.product.publishedScope, it.product.status, it.product.tags, it.product.title,
            it.product.updatedAt, it.product.variants, it.product.vendor, false, 0)
        handleUIViewPager(it.product.images)
        val words = it.product.title.lowercase().split(" ")
        var productName = ""
        words.forEach { word ->
            productName += word.replaceFirstChar { it.uppercase() } + " "
        }
        binding.txtViewProductName.text = productName.trim()
        binding.txtViewProductPrice.text = it.product.variants[0].price
        binding.txtViewCurrency.text = viewModel.getCurrencyWithUserEmail(myView.context)
        binding.txtViewDescription.text = it.product.bodyHtml

        val reviewsList = listOf(
            Review("Emad Nashaat",2.5,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSa69_HGc_i3MXKCPZzCfAjBZC4bXJsn0rS0Ufe6H-ctZz5FbIVaPkd1jCPTpKwPruIT3Q&usqp=CAU","the material wasn't good"),
            Review("Asmaa Galal",3.0,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5WYwjOWQCZ3g-4zrImh6lIDS7q50T4sE7S-_OeljDtY3M6pqAYqFNpKzi-t33hglOahk&usqp=CAU","good product but the packaging was bad"),
            Review("Hoda Ahmed",4.0,"https://cdn3.vectorstock.com/i/1000x1000/88/92/face-young-woman-in-frame-circular-avatar-vector-28828892.jpg","Very good quality like the description"),
        )

        reviewsAdapter.setDataToAdapter(reviewsList)
    }

    private fun handleUIActions() {
        binding.appBarHome.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardViewIsFavorite.setOnClickListener {
            if (viewModel.isUserLogin(myView.context)) {
                if (isFavoriteProduct) {
                    showDialog(
                        requireActivity(),
                        getString(R.string.warning),
                        getString(R.string.deleteWarning),
                        getString(R.string.delete),
                        ::removeFromFav
                    )
                } else {
                    viewModel.insertToFavorite(product)
                    handleFavorite(true, R.drawable.ic_favorite)
                }
            } else {
                showDialog(
                    requireActivity(),
                    getString(R.string.warning),
                    getString(R.string.loginWarning),
                    getString(R.string.login),
                    ::navigateToLogin
                )
            }
        }

        binding.btnReviewsMore.setOnClickListener {
            val action = ProductDetailsFragmentDirections.actionProductDetailsFragmentToReviewsFragment()
            mNavController.navigate(action)
        }

        binding.btnReviewsAddToBag.setOnClickListener {
            if (viewModel.isUserLogin(myView.context)) {
                if (isAddingToShoppingCart) {
                    showDialog(
                        requireActivity(),
                        getString(R.string.warning),
                        getString(R.string.deleteWarningCart),
                        getString(R.string.delete),
                        ::removeFromBag
                    )
                } else {
                    productDetail.amount = 1
                    viewModel.insertProductInShoppingCart(productDetail)
                    handleBag(true, getString(R.string.remove_from_bag))
                }
            } else {
                showDialog(
                    requireActivity(),
                    getString(R.string.warning),
                    getString(R.string.loginWarning),
                    getString(R.string.login),
                    ::navigateToLogin
                )
            }
        }
    }

    fun removeFromFav(){
        viewModel.deleteFromFavorite(product)
        handleFavorite(false, R.drawable.ic_favorite_border)
    }

    fun removeFromBag(){
        viewModel.deleteProductFromShoppingCart(productDetail)
        handleBag(false, getString(R.string.add_to_bag))
    }

    private fun navigateToLogin(){
        mNavController.navigate(R.id.action_productDetailsFragment_to_loginFragment)
    }

    private fun handleFavorite(isAddToFav: Boolean, imgResourceId: Int) {
        isFavoriteProduct = isAddToFav
        binding.imgViewFavoriteIcon.setImageResource(imgResourceId)
    }

    private fun handleBag(isAddToShopCart: Boolean, btnText: String) {
        isAddingToShoppingCart = isAddToShopCart
        binding.btnReviewsAddToBag.text = btnText
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
        binding.viewPagerAdsSlider.adapter = SliderProductDetailsAdapter(myView.context, images, this)
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

        TabLayoutMediator(
            binding.tabLayout, binding.viewPagerAdsSlider
        ) { tab: TabLayout.Tab?, position: Int -> }.attach()
    }

    private var sliderRunnable = Runnable {
        if (binding.viewPagerAdsSlider.currentItem + 1 == sliderImages.size) {
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

    override fun onImageClick(position: Int) {
        dialog = Dialog(myView.context)
        dialog.setContentView(R.layout.product_details_image)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        Glide
            .with(myView.context)
            .load(sliderImages[position].src)
            .into(dialog.findViewById(R.id.imgViewProductImage))

        dialog.show()
    }

    fun showDeleteDialog(requireActivity: Activity, dialogTitle:String, dialogMessage:String, btnName:String, deleteHandle:() -> Unit){
        val inflater = requireActivity.layoutInflater
        val dialog = Dialog(requireActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bind : GoToLoginDialogBinding = GoToLoginDialogBinding.inflate(inflater)
        dialog.setContentView(bind.root)
        dialog.setTitle(dialogTitle)
        bind.warningTitle.text = dialogMessage
        bind.goToLogin.text = btnName
        bind.okBtn.text = getString(R.string.cancel)
        bind.okBtn.setOnClickListener {
            dialog.dismiss()
        }
        bind.goToLogin.setOnClickListener{
            deleteHandle()
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }
}