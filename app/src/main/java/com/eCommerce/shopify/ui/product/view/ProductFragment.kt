package com.eCommerce.shopify.ui.product.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentProductBinding
import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.product.repo.ProductRepo
import com.eCommerce.shopify.ui.product.viewmodel.ProductViewModel
import com.eCommerce.shopify.ui.product.viewmodel.ProductViewModelFactory
import com.eCommerce.shopify.utils.AppConstants

class ProductFragment : Fragment(), OnCategoryProductClickListener {

    private lateinit var productViewModelFactory: ProductViewModelFactory
    private lateinit var viewModel: ProductViewModel
    private var _binding: FragmentProductBinding? = null

    private lateinit var productAdapter: ProductAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var myView: View

    private val mNavController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    private val args by navArgs<ProductFragmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        gettingViewModelReady()
        handleUIEvents()
        initRecyclerView()
    }

    private fun gettingViewModelReady() {
        productViewModelFactory = ProductViewModelFactory(
            ProductRepo.getInstance(
                APIClient.getInstance()
            )
        )
        viewModel = ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]

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
        viewModel.categoryProductsResponse.observe(viewLifecycleOwner) {
            renderDataOnScreen(it)
        }
    }

    private fun renderDataOnScreen(it: Products) {
        productAdapter.setDataToAdapter(it.products)
    }

    private fun initRecyclerView() {
        productAdapter = ProductAdapter(myView.context, emptyList(), this)
        gridLayoutManager = GridLayoutManager(myView.context, 2, RecyclerView.VERTICAL, false)
        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = gridLayoutManager
        }

        viewModel.getCategoryProducts(args.categoryId)
    }

    private fun handleUIEvents() {
        binding.subCategoryRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioBtnAll -> {

                }
                R.id.radioBtnAccessories -> {

                }
                R.id.radioBtnTShirts -> {

                }
                R.id.radioBtnShoes -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCategoryProductClick(product: Product) {

    }
}