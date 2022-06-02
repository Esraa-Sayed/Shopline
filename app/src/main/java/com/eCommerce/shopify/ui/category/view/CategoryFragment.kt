package com.eCommerce.shopify.ui.category.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentCategoryBinding
import com.eCommerce.shopify.model.CustomCollection
import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.MainFragmentDirections
import com.eCommerce.shopify.ui.category.repo.CategoryRepo
import com.eCommerce.shopify.ui.category.viewmodel.CategoryViewModel
import com.eCommerce.shopify.ui.category.viewmodel.CategoryViewModelFactory
import com.eCommerce.shopify.utils.AppConstants

class CategoryFragment : Fragment(), OnCategoryClickListener {

    private lateinit var categoryViewModelFactory: CategoryViewModelFactory
    private lateinit var viewModel: CategoryViewModel
    private var _binding: FragmentCategoryBinding? = null

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var myView: View

    private val mNavController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myView = view
        gettingViewModelReady()
        initRecyclerView()
    }

    private fun gettingViewModelReady() {
        categoryViewModelFactory = CategoryViewModelFactory(
            CategoryRepo.getInstance(
                APIClient.getInstance()
            )
        )
        viewModel = ViewModelProvider(this, categoryViewModelFactory)[CategoryViewModel::class.java]

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
        viewModel.customCollectionsCategoryResponse.observe(viewLifecycleOwner) {
            renderDataOnScreen(it)
        }
    }

    private fun renderDataOnScreen(it: CustomCollectionsCategory) {
        categoryAdapter.setDataToAdapter(it.customCollections)
    }

    private fun initRecyclerView() {
        categoryAdapter = CategoryAdapter(myView.context, emptyList(), this)
        linearLayoutManager = LinearLayoutManager(myView.context, RecyclerView.VERTICAL, false)
        binding.recyclerView.apply {
            adapter = categoryAdapter
            layoutManager = linearLayoutManager
        }

        viewModel.getCustomCollectionsCategory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCategoryClick(customCollection: CustomCollection) {
        val action = MainFragmentDirections.actionMainFragmentToProductFragment(customCollection.id, customCollection.title)
        mNavController.navigate(action)
    }
}